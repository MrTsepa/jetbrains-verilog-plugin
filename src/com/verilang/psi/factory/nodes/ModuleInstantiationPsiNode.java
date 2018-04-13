package com.verilang.psi.factory.nodes;

import com.intellij.lang.ASTNode;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

public class ModuleInstantiationPsiNode extends ANTLRPsiNode {

    public ModuleInstantiationPsiNode(@NotNull ASTNode node) {
        super(node);
    }

}
