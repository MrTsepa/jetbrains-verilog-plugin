package com.verilang.psi;

import com.intellij.lang.ASTNode;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

public class ParameterDeclaration extends ANTLRPsiNode implements TypedDeclaration {

    public ParameterDeclaration(@NotNull ASTNode node) {
        super(node);
    }

    public String getTypeText() {
        return getFirstChild().getFirstChild().getText();
    }

}
