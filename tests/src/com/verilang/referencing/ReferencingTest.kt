package com.verilang.referencing

import com.intellij.psi.impl.source.resolve.reference.impl.PsiMultiReference
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import com.verilang.VerilogFileType
import com.verilang.psi.factory.nodes.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*


class ReferencingTest : LightCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String {
        return "tests/resources"
    }

    fun `test reference to reg`() {
        myFixture.configureByText(VerilogFileType.INSTANCE, """
            module toplevel
            (input clock,
             input reset,
             input d,
             output reg flop2
            );

                reg flop1;
                always @ (posedge reset, posedge clock)
                if (reset)
                    {flop1,flop2} <= 2'b00;
                else
                    begin
                        flop1 <= d;
                        flop2 <= flop<caret>1;
                    end
            endmodule""".trimIndent())
        val reference = myFixture.getReferenceAtCaretPositionWithAssertion()
        assertThat(PsiTreeUtil.getParentOfType(
                reference.resolve(), RegDeclarationPsiNode::class.java
        )?.text, `is`("reg flop1;"))
    }

    fun `test reference to input port`() {
        myFixture.configureByText(VerilogFileType.INSTANCE, """
            module toplevel
            (input clock,
             input reset,
             input d,
             output reg flop2
            );

                reg flop1;
                always @ (posedge reset, posedge clock)
                if (res<caret>et)
                    {flop1,flop2} <= 2'b00;
                else
                    begin
                        flop1 <= d;
                        flop2 <= flop1;
                    end
            endmodule""".trimIndent())
        val reference = myFixture.getReferenceAtCaretPositionWithAssertion()
        assertThat(PsiTreeUtil.getParentOfType(
                reference.resolve(), PortDeclarationPsiNode::class.java
        )?.text, `is`("input reset"))
    }

    fun `test reference to parameter`() {
        myFixture.configureByText(VerilogFileType.INSTANCE, """
            module toplevel
            (input clock,
             input reset,
             input d,
             output reg flop2
            );
            	parameter TXvel = 1;
                wire ce = TX<caret>vel;
                reg flop1;
                always @ (posedge reset, posedge clock)
                if (reset)
                    {flop1,flop2} <= 2'b00;
                else
                    begin
                        flop1 <= d;
                        flop2 <= flop1;
                    end
            endmodule""".trimIndent())
        val reference = myFixture.getReferenceAtCaretPositionWithAssertion()
        assertThat(PsiTreeUtil.getParentOfType(
                reference.resolve(), ParameterDeclarationPsiNode::class.java
        )?.text, `is`("parameter TXvel = 1;"))
    }

    fun `test reference to module` () {
        myFixture.copyFileToProject("Foo.v")
        myFixture.configureByText(VerilogFileType.INSTANCE, """
module Bar;
    wire bar;
    Fo<caret>o uut (
        .foo(bar)
    );
endmodule
            """
        )
        val reference = myFixture.getReferenceAtCaretPositionWithAssertion()
        assertThat(reference.resolve()?.text, `is`("module Foo (\n\tinput foo\n\t);\n\nendmodule"))
    }

    fun `test reference to port in outer module` () {
        myFixture.copyFileToProject("Foo.v")
        myFixture.configureByText(VerilogFileType.INSTANCE, """
module Bar;
    wire bar;
    Foo uut (
        .f<caret>oo(bar)
    );
endmodule
            """
        )
        val reference = myFixture.getReferenceAtCaretPositionWithAssertion()
        if (reference is PsiMultiReference) {
            assertThat(PsiTreeUtil.getParentOfType(
                    reference.multiResolve(false)[0].element, PortDeclarationPsiNode::class.java
            )?.text, `is`("input foo"))
        } else {
            assertThat(PsiTreeUtil.getParentOfType(
                    reference.resolve(), PortDeclarationPsiNode::class.java
            )?.text, `is`("input foo"))
        }
    }

    fun `test reference to port in inner module` () {
        myFixture.copyFileToProject("Foo.v")
        myFixture.configureByText(VerilogFileType.INSTANCE, """
module Bar;
    wire bar;
    Foo uut (
        .foo(ba<caret>r)
    );
endmodule
            """
        )
        val reference = myFixture.getReferenceAtCaretPositionWithAssertion()
        assertThat(PsiTreeUtil.getParentOfType(
                reference.resolve(), NetDeclarationPsiNode::class.java
        )?.text, `is`("wire bar;"))
    }
}
