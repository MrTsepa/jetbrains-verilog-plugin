package com.verilang.psi;

import com.intellij.lang.ASTNode;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

public class PortDeclarationPsiNode extends ANTLRPsiNode implements TypedDeclaration {

    public PortDeclarationPsiNode(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public String getTypeText() {
        return this.getLastChild().getFirstChild().getText();
    }

}
