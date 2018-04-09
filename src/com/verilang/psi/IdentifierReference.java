package com.verilang.psi;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class IdentifierReference extends PsiReferenceBase<IdentifierPsiLeafNode> {

    public IdentifierReference(@NotNull IdentifierPsiLeafNode element) {
        super(element, new TextRange(0, element.getText().length()));
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return myElement.setName(newElementName);
    }

    @Override
    public boolean isReferenceTo(PsiElement element) {
        if (element instanceof IdentifierPsiLeafNode) {
            return Objects.equals(((IdentifierPsiLeafNode) element).getName(), myElement.getName());
        }
        return false;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        return myElement;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }
}
