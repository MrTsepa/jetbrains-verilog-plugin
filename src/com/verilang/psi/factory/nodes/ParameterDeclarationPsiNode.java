package com.verilang.psi.factory.nodes;

import com.intellij.lang.ASTNode;
import com.verilang.psi.TypedDeclaration;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

public class ParameterDeclarationPsiNode extends ANTLRPsiNode implements TypedDeclaration {

    public ParameterDeclarationPsiNode(@NotNull ASTNode node) {
        super(node);
    }

    public String getTypeText() {
        return getFirstChild().getFirstChild().getText();
    }

}
