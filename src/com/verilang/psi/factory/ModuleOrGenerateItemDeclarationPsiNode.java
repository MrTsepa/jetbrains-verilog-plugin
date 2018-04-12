package com.verilang.psi.factory;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.verilang.psi.TypedDeclaration;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

public class ModuleOrGenerateItemDeclarationPsiNode
        extends ANTLRPsiNode
        implements TypedDeclaration {

    public ModuleOrGenerateItemDeclarationPsiNode(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public String getTypeText() {
        // We are sure that there is always only one non-leaf child
        PsiElement child = this.getFirstChild();

        // all declarations except "net_declaration" has their
        // type as first child literal.
        // "net_declaration" first child is either "net_type" or literal "trireg".
        // anyway all names of this types can be got by getText()
        return child.getFirstChild().getText();
    }

}
