package com.verilang.completion

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import com.verilang.VerilogFileType
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*


class CompletionTest : LightCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String {
        return "tests/resources"
    }

    fun `test references completion`() {
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
                        flop2 <= flop1;
                        <caret>
                    end
            endmodule""".trimIndent())
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings ?: throw Exception()
        assertThat(strings, hasItems(
                "flop1",
                "flop2",
                "clock",
                "reset",
                "d"
        ))
    }

    fun `test keyword completion`() {
        myFixture.configureByText(VerilogFileType.INSTANCE, """
            module toplevel
            (input clock,
             input reset,
             input d,
             output reg flop2
            );
                <caret>
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
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings ?: throw Exception()
        assertThat(strings, hasItems(
                "reg", "wire", "assign", "always", "initial"
        ))
    }

    fun `test directive keyword completion`() {
        myFixture.configureByText(VerilogFileType.INSTANCE, """
`<caret>

module toplevel;
    wire bar;

endmodule""".trimIndent())
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings ?: throw Exception()
        assertThat(strings, hasItems(
                "include", "timescale", "default_nettype"
        ))
        assertThat(strings, not(hasItem("module")))
    }

    fun `test by multiple files outer`() {
        myFixture.copyFileToProject("Foo.v")
        myFixture.configureByText(VerilogFileType.INSTANCE, """
module Bar;

    wire bar;

    Foo uut (
        .<caret>
    );

endmodule
            """
        )
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings ?: throw Exception()
        assertThat(strings, hasItem("foo"))
        assertThat(strings, not(hasItem("bar")))
    }

    fun `test by multiple files inner`() {
        myFixture.copyFileToProject("Foo.v")
        myFixture.configureByText(VerilogFileType.INSTANCE, """
module Bar;

    wire bar;

    MIL_TXD uut (
        .foo(<caret>
    );

endmodule
            """
        )
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings ?: throw Exception()
        assertThat(strings, hasItems("bar"))
    }

    fun `test by multiple files outer module name`() {
        myFixture.copyFileToProject("Foo.v")
        myFixture.configureByText(VerilogFileType.INSTANCE, """
module Bar;

    wire bar;

    <caret>

endmodule
            """
        )
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings ?: throw Exception()
        assertThat(strings, hasItem("Foo"))
        assertThat(strings, not(hasItem("Bar")))
    }

    fun `test by multiple files outer module name 2`() {
        myFixture.copyFileToProject("Foo.v")
        myFixture.configureByText(VerilogFileType.INSTANCE, """
module Bar;

    <caret>

endmodule
            """
        )
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings ?: throw Exception()
        assertThat(strings, hasItem("Foo"))
        assertThat(strings, not(hasItem("Bar")))
    }

    fun `test by multiple files outer module name 3`() {
        myFixture.copyFileToProject("Foo.v")
        myFixture.copyFileToProject("Biz.v")
        myFixture.configureByText(VerilogFileType.INSTANCE, """
module Bar;

    <caret>

endmodule
            """
        )
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings ?: throw Exception()
        assertThat(strings, hasItems("Foo", "Biz"))
        assertThat(strings, not(hasItems("Bar")))
    }

    fun `test by multiple files outer module name 4`() {
        myFixture.copyFileToProject("Foo.v")
        myFixture.copyFileToProject("Biz.v")
        myFixture.configureByText(VerilogFileType.INSTANCE, """
module Bar;

    wire bar;

    Foo f (
        .foo(bar)
    );

    <caret>

endmodule
            """
        )
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings ?: throw Exception()
        assertThat(strings, hasItem("Biz"))
        assertThat(strings, not(hasItem("Bar")))
        assertThat(strings, not(hasItem("Foo")))
    }

    fun `test by multiple files outer module name 5`() {
        myFixture.copyFileToProject("Foo.v")
        myFixture.copyFileToProject("Biz.v")
        myFixture.configureByText(VerilogFileType.INSTANCE, """
module Bar;

    wire bar;

    <caret>

    Foo f (
        .foo(bar)
    );

endmodule
            """
        )
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings ?: throw Exception()
        assertThat(strings, hasItem("Biz"))
        assertThat(strings, not(hasItem("Bar")))
        assertThat(strings, not(hasItem("Foo")))
    }

}
