package com.verilang.psi;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.util.PsiTreeUtil;
import com.verilang.VerilogFileType;
import com.verilang.psi.factory.nodes.IdentifierPsiNode;
import com.verilang.psi.factory.nodes.SimpleIdentifierPsiLeafNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;


public class SimpleIdentifierReference extends PsiReferenceBase<SimpleIdentifierPsiLeafNode> {

    public SimpleIdentifierReference(@NotNull SimpleIdentifierPsiLeafNode element) {
        super(element, new TextRange(0, element.getText().length()));
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        PsiFile file = myElement.getContainingFile();
        Collection<IdentifierPsiNode> identifierPsiNodes =
                PsiTreeUtil.findChildrenOfType(file, IdentifierPsiNode.class);
        return identifierPsiNodes.stream()
                .filter(identifierPsiNode ->
                        Objects.equals(identifierPsiNode.getName(), myElement.getName()))
                .findFirst().orElse(null);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        PsiFile file = myElement.getContainingFile();
        Collection<IdentifierPsiNode> identifierPsiNodes =
                PsiTreeUtil.findChildrenOfType(file, IdentifierPsiNode.class);
        return identifierPsiNodes.stream()
                .map(node -> {
                    TypedDeclaration typedDeclarationParent =
                            PsiTreeUtil.getParentOfType(
                                    node,
                                    TypedDeclaration.class
                            );
                    if (typedDeclarationParent != null) {
                        String typeText = typedDeclarationParent.getTypeText();
                        return LookupElementBuilder.create(node)
                                .withIcon(VerilogFileType.INSTANCE.getIcon())
                                .withTypeText(typeText);
                    }

                    return LookupElementBuilder.create(node)
                            .withIcon(VerilogFileType.INSTANCE.getIcon());
                }).toArray();
    }

}
