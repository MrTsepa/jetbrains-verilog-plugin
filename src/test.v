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
   end
endmodule //toplevel