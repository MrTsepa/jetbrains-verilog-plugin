package com.verilang.psi;

import com.intellij.psi.PsiReference;
import com.intellij.psi.tree.IElementType;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiLeafNode;

public class SimpleIdentifierPsiLeafNode extends ANTLRPsiLeafNode {

    public SimpleIdentifierPsiLeafNode(IElementType type, CharSequence text) {
        super(type, text);
    }

    @Override
    public String getName() {
        return this.getText();
    }

    @Override
    public PsiReference getReference() {
        return new SimpleIdentifierReference(this);
    }
}
