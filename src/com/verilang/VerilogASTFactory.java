package com.verilang;

import com.intellij.core.CoreASTFactory;
import com.intellij.lang.Language;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.tree.IElementType;
import com.verilang.psi.factory.SimpleIdentifierPsiLeafNode;
import com.verilang.psi.factory.VerilogPsiLeafNodeFactory;
import org.antlr.jetbrains.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.jetbrains.adaptor.lexer.TokenIElementType;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiLeafNode;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


public class VerilogASTFactory extends CoreASTFactory {

    @NotNull
    @Override
    public LeafElement createLeaf(@NotNull IElementType type, @NotNull CharSequence text) {
        return VerilogPsiLeafNodeFactory.createLeaf(type, text);
    }

}
