package com.verilang.psi.factory.nodes;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.verilang.psi.TypedDeclaration;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PortDeclarationPsiNode extends ANTLRPsiNode implements TypedDeclaration {

    public PortDeclarationPsiNode(@NotNull ASTNode node) {
        super(node);
    }

    /**
     * in {input|output|inout}_declaration children except last child is about type,
     * e.g. "output wire signed [15:0] OUTPUT_NAME", so we will pass it to typeText
     */
    @Override
    public String getTypeText() {
        PsiElement[] typeChildren = Arrays.copyOf(
                this.getLastChild().getChildren(),
                this.getLastChild().getChildren().length - 1
        );
        return String.join(" ",
                Arrays.stream(typeChildren)
                        .map(PsiElement::getText)
                        .collect(Collectors.toList())
        );
    }

}
