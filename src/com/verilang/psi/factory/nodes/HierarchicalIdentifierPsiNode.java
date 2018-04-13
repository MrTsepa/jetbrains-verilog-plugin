package com.verilang.psi.factory.nodes;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiReference;
import com.intellij.psi.util.PsiTreeUtil;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;


public class HierarchicalIdentifierPsiNode extends ANTLRPsiNode {

    public HierarchicalIdentifierPsiNode(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public PsiReference[] getReferences() {
        return PsiTreeUtil.getChildrenOfTypeAsList(
                this.getNode().getPsi(),
                SimpleIdentifierPsiLeafNode.class
        ).stream()
                .map(SimpleIdentifierPsiLeafNode::getReference)
                .toArray(PsiReference[]::new);
    }
}
