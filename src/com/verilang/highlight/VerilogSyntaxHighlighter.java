package com.verilang.highlight;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import com.verilang.VerilogLanguage;
import com.verilang.VerilogLexer;
import org.antlr.jetbrains.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.jetbrains.adaptor.lexer.TokenIElementType;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class VerilogSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey LINE_COMMENT =
            createTextAttributesKey(
                    "VERILOG_LINE_COMMENT",
                    DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey BLOCK_COMMENT =
            createTextAttributesKey(
                    "VERILOG_BLOCK_COMMENT",
                    DefaultLanguageHighlighterColors.BLOCK_COMMENT);
    public static final TextAttributesKey SEMICOLON =
            createTextAttributesKey(
                    "VERILOG_SEMICOLON",
                    DefaultLanguageHighlighterColors.SEMICOLON
            );
    public static final TextAttributesKey KEYWORD =
            createTextAttributesKey(
                    "VERILOG_KEYWORD",
                    DefaultLanguageHighlighterColors.KEYWORD
            );

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        VerilogLexer lexer = new VerilogLexer(null);
        return new ANTLRLexerAdaptor(VerilogLanguage.INSTANCE, lexer);
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType iElementType) {
        TokenIElementType tokenIElementType = (TokenIElementType) iElementType;
        int antlrTokenType = tokenIElementType.getANTLRTokenType();
        switch (antlrTokenType) {
            case VerilogLexer.Block_comment:
                return new TextAttributesKey[] {BLOCK_COMMENT};
            case VerilogLexer.One_line_comment:
                return new TextAttributesKey[] {LINE_COMMENT};
            case VerilogLexer.T__1:
                return new TextAttributesKey[] {SEMICOLON};
            case VerilogLexer.T__11:
            case VerilogLexer.T__12:
            case VerilogLexer.T__14:
            case VerilogLexer.T__98:
            case VerilogLexer.T__99:
            case VerilogLexer.T__100:
            case VerilogLexer.T__101:
            case VerilogLexer.T__102:
            case VerilogLexer.T__103:
            case VerilogLexer.T__104:
                return new TextAttributesKey[] {KEYWORD};
        }
        return new TextAttributesKey[0];
    }

}
