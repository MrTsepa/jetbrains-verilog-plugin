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

    fun `test unresolved reference module instantiation`() {
        myFixture.configureByText(VerilogFileType.INSTANCE, """
module Bar;
    wire bar;
    <error descr="Unresolved reference">Foo</error> uut (
        .<error descr="Unresolved reference">foo</error>(bar)
    );
endmodule""".trimIndent())
        myFixture.testHighlighting(false, false, false)
    }

    fun `test unresolved reference module instantiation and local reference`() {
        myFixture.configureByText(VerilogFileType.INSTANCE, """
module Bar;
    <error descr="Unresolved reference">Foo</error> uut (
        .<error descr="Unresolved reference">foo</error>(<error descr="Unresolved reference">bar</error>)
    );
endmodule""".trimIndent())
        myFixture.testHighlighting(false, false, false)
    }

    fun `test all references are ok`() {
        myFixture.configureByText(VerilogFileType.INSTANCE, """
module MIL_TXD (
	input clk,
	input[15:0] dat,
	input txen,

	output wire TXP,
	output wire TXN,
	output wire SY1,
	output wire SY2,
	output reg [15:0] en_tx = 0,
	output reg T_dat = 0,
	output wire T_end,
	output wire SDAT,
	output reg FT_cp=0,
	output reg [4:0]cb_bit =0,
	output wire ce_tact
	);

	parameter TXvel = 1000000;
	parameter Fclk = 50000000;
	reg [5:0] cb_ce = 0;
	wire ce = (cb_ce == (Fclk / (2 * TXvel)));
	reg bf_SY1 = 0, bf_SY2 = 0;
	reg CW_DW = 0;
	reg ttxen = 0;
	reg QM = 0;
	assign ce_tact = ce & QM;
	wire start = ttxen & !en_tx;
	assign SY1 = en_tx & bf_SY1;
	assign SY2 = en_tx & bf_SY2;
	reg [15:0] sr_dat = 0;
	wire st1 = (cb_bit == 1) & en_tx;
	wire st2 = (cb_bit == 2) & en_tx;
	wire st18 = (cb_bit == 18) & en_tx;
	assign T_end = (cb_bit==19) & en_tx;

	assign TXP = (en_tx & 	(( CW_DW & SY1) |
	            (!CW_DW & SY2) |
				(T_dat & (sr_dat[15]^QM)) |
				(T_end & (FT_cp^QM)))) ^ ((T_dat | T_end) & ce_tact);
	assign TXN = (en_tx & 	((!CW_DW & SY1) |
				(CW_DW & SY2) |
				(T_dat & (sr_dat[15]^!QM)) |
				(T_end & (FT_cp^!QM)))) ^ ((T_dat | T_end) & ce);
    assign SDAT = sr_dat[15] & T_dat;

    always @ (posedge clk) begin
        cb_ce <= ce ? 1 : cb_ce+1; // Tcet = Tbit/2 = 0.5us
        QM <= (ce | start)? !QM : QM;
    end

    always @ (posedge clk) if (ce) begin
        bf_SY1 <= (st1 & !QM) ? 0 : ((T_end & QM) | (txen & !en_tx)) ? 1 : bf_SY1;
        bf_SY2 <= (st1 & !QM) ? 1 : (st2 & QM)? 0 : bf_SY2;
    end

    always @ (posedge clk) if (ce_tact) begin
        ttxen <= txen;
        en_tx <= ttxen ? 1 : (!txen & T_end) ? 0 : en_tx;
        cb_bit <= (!en_tx | T_end) ? 0 : en_tx? cb_bit + 1 : cb_bit;
        T_dat <= st2 ? 1 : st18 ? 0 : T_dat;
        sr_dat <= st2 ? dat : T_dat ? sr_dat<<1 : sr_dat;
        FT_cp <= st2 ? 1 : (T_dat & sr_dat[15] )? !FT_cp : FT_cp;
        CW_DW <= start ? 1 : T_end ? 0 : CW_DW;
    end

endmodule

""".trimIndent())
        myFixture.testHighlighting(false, false, false)
    }
}