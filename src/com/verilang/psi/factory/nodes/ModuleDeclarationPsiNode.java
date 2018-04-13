package com.verilang.psi.factory.nodes;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.verilang.VerilogLexer;
import com.verilang.psi.ScopeNode;
import com.verilang.psi.factory.VerilogPsiLeafNodeFactory;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ModuleDeclarationPsiNode extends ANTLRPsiNode
        implements PsiNameIdentifierOwner, ScopeNode {

    public ModuleDeclarationPsiNode(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public String getName() {
        if (getNameIdentifier() == null)
            return null;
        return getNameIdentifier().getText();
    }

    @Nullable
    @Override
    public PsiElement getNameIdentifier() {
        for (PsiElement psiElement : getChildren()) {
            if (psiElement instanceof ModuleIdentifierPsiNode) {
                return psiElement.getFirstChild();
            }
        }
        return null;
    }

    @Override
    public PsiElement setName(@NotNull String s) throws IncorrectOperationException {
        PsiElement nameIdentifier = this.getNameIdentifier();
        if (nameIdentifier == null) {
            return null;
        }
        return nameIdentifier.replace(
                VerilogPsiLeafNodeFactory.create(VerilogLexer.Simple_identifier, s));
    }

    @Override
    public Collection<PsiNamedElement> getAvailableNamedElements() {
        return Stream.of(
                PsiTreeUtil.findChildrenOfType(this, NetIdentifierPsiNode.class).stream(),
                PsiTreeUtil.findChildrenOfType(this, ParameterIdentifierPsiNode.class).stream(),
                PsiTreeUtil.findChildrenOfType(this, PortIdentifierPsiNode.class).stream()
                        .filter(portIdentifierPsiNode -> PsiTreeUtil.getParentOfType(
                                portIdentifierPsiNode,
                                ModuleInstantiationPsiNode.class
                        ) == null)
        ).flatMap(stream -> stream)
                .collect(Collectors.toList());
    }
}
