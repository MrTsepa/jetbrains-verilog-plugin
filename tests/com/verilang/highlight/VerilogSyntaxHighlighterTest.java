package com.verilang.highlight;

import com.verilang.VerilogLexer;
import org.junit.Test;

import static org.junit.Assert.*;

public class VerilogSyntaxHighlighterTest {

    @Test
    public void getTypeForLiteralName() {
         assertEquals(
                 VerilogLexer.T__12,
                 VerilogSyntaxHighlighter.getTypeForLiteralName("module")
         );
        assertEquals(
                VerilogLexer.T__98,
                VerilogSyntaxHighlighter.getTypeForLiteralName("if")
        );
    }
}