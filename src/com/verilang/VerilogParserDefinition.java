package com.verilang;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.verilang.psi.VerilogFile;
import org.antlr.jetbrains.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.jetbrains.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.jetbrains.adaptor.parser.ANTLRParserAdaptor;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jetbrains.annotations.NotNull;

public class VerilogParserDefinition implements ParserDefinition {

    static {
        PSIElementTypeFactory.defineLanguageIElementTypes(
                VerilogLanguage.INSTANCE,
                VerilogParser.tokenNames,
                VerilogParser.ruleNames
        );
    }

    public static final TokenSet WHITESPACES =
            PSIElementTypeFactory.createTokenSet(
                    VerilogLanguage.INSTANCE,
                    VerilogLexer.White_space
            );

    public static final TokenSet COMMENTS =
            PSIElementTypeFactory.createTokenSet(
                    VerilogLanguage.INSTANCE,
                    VerilogLexer.Block_comment,
                    VerilogLexer.One_line_comment
            );

    public static final TokenSet STRINGS =
            PSIElementTypeFactory.createTokenSet(
                    VerilogLanguage.INSTANCE,
                    VerilogLexer.String
            );

    public static final IFileElementType FILE = new IFileElementType(VerilogLanguage.INSTANCE);

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return WHITESPACES;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return STRINGS;
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new VerilogFile(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        VerilogLexer lexer = new VerilogLexer(null);
        return new ANTLRLexerAdaptor(VerilogLanguage.INSTANCE, lexer);
    }

    @NotNull
    public PsiParser createParser(final Project project) {
        final VerilogParser parser = new VerilogParser(null);
        return new ANTLRParserAdaptor(VerilogLanguage.INSTANCE, parser) {
            @Override
            protected ParseTree parse(Parser parser, IElementType root) {
                System.out.println(root.getClass());
                return ((VerilogParser) parser).source_text();
            }
        };
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return new ANTLRPsiNode(node);
    }

}
