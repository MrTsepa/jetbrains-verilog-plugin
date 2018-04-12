package com.verilang.psi.factory;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.verilang.psi.TypedDeclaration;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class NetDeclarationPsiNode extends ANTLRPsiNode implements TypedDeclaration {

    public NetDeclarationPsiNode(@NotNull ASTNode node) {
        super(node);
    }

    /**
     * in net_declaration all children except two last children is about type,
     * e.g. "wire signed [15:0] WIRE_NAME;", so we will pass it to typeText
     */
    @Override
    public String getTypeText() {
        PsiElement[] typeChildren = Arrays.copyOf(
                this.getChildren(),
                this.getChildren().length - 2
        );
        return String.join(" ",
                Arrays.stream(typeChildren)
                        .map(PsiElement::getText)
                        .collect(Collectors.toList())
        );
    }
}
