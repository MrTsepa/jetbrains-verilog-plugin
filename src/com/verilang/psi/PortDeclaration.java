package com.verilang.psi;

import com.intellij.lang.ASTNode;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

public class PortDeclaration extends ANTLRPsiNode implements TypedDeclaration {

    public PortDeclaration(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public String getTypeText() {
        return this.getLastChild().getFirstChild().getText();
    }

}
