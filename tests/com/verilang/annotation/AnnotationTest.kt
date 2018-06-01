package com.verilang.annotation

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import com.verilang.VerilogFileType

class AnnotationTest : LightCodeInsightFixtureTestCase() {
    fun `test unresolved reference`() {
        myFixture.configureByText(VerilogFileType.INSTANCE, """
            module toplevel
            (input clock,
             input reset,
             input d,
             output reg flop2
            );

                reg flop1;
                always @ (posedge <error descr="Unresolved reference">rese</error>, posedge clock)
                if (reset)
                    {flop1,flop2} <= 2'b00;
                else
                    begin
                        flop1 <= d;
                        flop2 <= <error descr="Unresolved reference">flo</error>;
                    end
            endmodule""".trimIndent())
        myFixture.testHighlighting(false, false, false)
    }
}