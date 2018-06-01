package com.verilang.highlight;

import com.verilang.VerilogLexer;
import org.junit.Test;

import static org.junit.Assert.*;

public class VerilogSyntaxHighlighterTest {

    @Test
    public void getTypeForLiteralName() {
         assertEquals(
                 VerilogLexer.K_module,
                 VerilogSyntaxHighlighter.getTypeForLiteralName("module")
         );
         assertEquals(
                 VerilogLexer.K_if,
                 VerilogSyntaxHighlighter.getTypeForLiteralName("if")
         );
    }
}