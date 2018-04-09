package com.verilang.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiLeafNode;
import org.jetbrains.annotations.NotNull;

public class IdentifierPsiLeafNode extends ANTLRPsiLeafNode implements PsiNamedElement {

    public IdentifierPsiLeafNode(IElementType type, CharSequence text) {
        super(type, text);
    }

    @Override
    public String getName() {
        return getText();
    }

    @Override
    public PsiElement setName(@NotNull String s) throws IncorrectOperationException {
        return this.replace(new ANTLRPsiLeafNode(this.getElementType(), s));
    }

    @Override
    public PsiReference getReference() {
        return new IdentifierReference(this);
    }
}
