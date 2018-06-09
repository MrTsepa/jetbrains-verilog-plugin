package com.verilang.psi.factory.nodes;

import com.intellij.lang.ASTNode;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

public class StatementPsiNode extends ANTLRPsiNode {

    public StatementPsiNode(@NotNull ASTNode node) {
        super(node);
    }

}
