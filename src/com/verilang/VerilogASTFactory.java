package com.verilang;

import com.intellij.core.CoreASTFactory;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.tree.IElementType;
import com.verilang.psi.factory.VerilogPsiLeafNodeFactory;
import org.jetbrains.annotations.NotNull;


public class VerilogASTFactory extends CoreASTFactory {

    @NotNull
    @Override
    public LeafElement createLeaf(@NotNull IElementType type, @NotNull CharSequence text) {
        return VerilogPsiLeafNodeFactory.create(type, text);
    }

}
