package com.verilang.highlight;

import com.intellij.ide.highlighter.custom.CustomHighlighterColors;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import com.verilang.VerilogKeywords;
import com.verilang.VerilogLanguage;
import com.verilang.VerilogLexer;
import org.antlr.jetbrains.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.jetbrains.adaptor.lexer.TokenIElementType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.IntStream;

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
    public static final TextAttributesKey COMMA =
            createTextAttributesKey(
                    "VERILOG_SEMICOLON",
                    DefaultLanguageHighlighterColors.COMMA
            );
    public static final TextAttributesKey KEYWORD =
            createTextAttributesKey(
                    "VERILOG_KEYWORD",
                    CustomHighlighterColors.CUSTOM_KEYWORD1_ATTRIBUTES
            );
    public static final TextAttributesKey NUMBER =
            createTextAttributesKey(
                    "VERILOG_NUMBER",
                    DefaultLanguageHighlighterColors.NUMBER
            );
    public static final TextAttributesKey STRING =
            createTextAttributesKey(
                    "VERILOG_STRING",
                    DefaultLanguageHighlighterColors.STRING
            );
    public static final TextAttributesKey IDENTIFIER =
            createTextAttributesKey(
                    "VERILOG_IDENTIFIER",
                    DefaultLanguageHighlighterColors.IDENTIFIER
            );
    public static final TextAttributesKey DOLLAR_IDENTIFIER =
            createTextAttributesKey(
                    "VERILOG_DOLLAR_IDENTIFIER",
                    CustomHighlighterColors.CUSTOM_KEYWORD2_ATTRIBUTES
            );
    public static final TextAttributesKey BAD_CHARACTER =
            createTextAttributesKey(
                    "VERILOG_BAD_CHARACTER",
                    HighlighterColors.BAD_CHARACTER
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
        final int type = tokenIElementType.getANTLRTokenType();

        if (type == VerilogLexer.Block_comment) {
            return new TextAttributesKey[]{BLOCK_COMMENT};
        } else if (type == VerilogLexer.One_line_comment) {
            return new TextAttributesKey[]{LINE_COMMENT};
        } else if (type == getTypeForLiteralName(";")) {
            return new TextAttributesKey[]{SEMICOLON};
        } else if (type == getTypeForLiteralName(",")) {
            return new TextAttributesKey[]{COMMA};
        } else if (
                IntStream.of(
                        VerilogLexer.Binary_number,
                        VerilogLexer.Octal_number,
                        VerilogLexer.Real_number,
                        VerilogLexer.Decimal_number,
                        VerilogLexer.Hex_number
                ).anyMatch(i -> i == type)) {
            return new TextAttributesKey[]{NUMBER};
        } else if (
                Arrays.stream(VerilogKeywords.VERILOG_KEYWORDS)
                        .mapToInt(VerilogSyntaxHighlighter::getTypeForLiteralName)
                        .anyMatch(i -> i == type)) {
            return new TextAttributesKey[]{KEYWORD};
        } else if (type == VerilogLexer.String) {
            return new TextAttributesKey[]{STRING};
        } else if (
                IntStream.of(
                        VerilogLexer.Simple_identifier,
                        VerilogLexer.Time_Identifier,
                        VerilogLexer.Escaped_identifier
                ).anyMatch(i -> i == type)) {
            return new TextAttributesKey[]{IDENTIFIER};
        } else if (type == VerilogLexer.Dollar_Identifier) {
            return new TextAttributesKey[]{DOLLAR_IDENTIFIER};
        } else if (type == VerilogLexer.Bad_character) {
            return new TextAttributesKey[]{BAD_CHARACTER};
        }
        return new TextAttributesKey[0];
    }

    /**
     * @param name one of literal names stored in {@link VerilogLexer#VOCABULARY}
     *             (e.g. "module" or "if")
     * @return antlrTokenType (aka int), from {@link VerilogLexer}
     *             or 0 if literal name not found
     */
    public static int getTypeForLiteralName(@NotNull String name) {
        for (int type = 0; type <= VerilogLexer.VOCABULARY.getMaxTokenType(); type++) {
            if (("'" + name + "'").equals(VerilogLexer.VOCABULARY.getLiteralName(type))) {
                return type;
            }
        }
        return 0;
    }

}
