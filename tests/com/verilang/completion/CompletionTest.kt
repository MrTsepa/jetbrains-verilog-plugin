package com.verilang.completion

import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import com.verilang.VerilogFileType
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*


class CompletionTest : LightCodeInsightFixtureTestCase() {


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

}
