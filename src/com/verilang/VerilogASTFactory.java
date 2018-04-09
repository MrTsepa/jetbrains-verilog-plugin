package com.verilang;

import com.intellij.core.CoreASTFactory;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.tree.IElementType;
import com.verilang.psi.IdentifierPsiLeafNode;
import org.antlr.jetbrains.adaptor.lexer.TokenIElementType;
import org.jetbrains.annotations.NotNull;

public class VerilogASTFactory extends CoreASTFactory {

    @NotNull
    @Override
    public LeafElement createLeaf(@NotNull IElementType type, @NotNull CharSequence text) {
        if (type instanceof TokenIElementType) {
            int antlrType = ((TokenIElementType) type).getANTLRTokenType();
            if (antlrType == VerilogLexer.Simple_identifier ||
                    antlrType == VerilogLexer.Escaped_identifier) {
                return new IdentifierPsiLeafNode(type, text);
            }
        }
        return super.createLeaf(type, text);
    }
}
