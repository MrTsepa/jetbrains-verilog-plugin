package com.verilang.psi;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.verilang.psi.factory.nodes.*;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.verilang.utils.FileUtilsKt.getAllVerilogFiles;


public class SimpleIdentifierReference extends PsiReferenceBase.Poly<SimpleIdentifierPsiLeafNode> {

    public SimpleIdentifierReference(@NotNull SimpleIdentifierPsiLeafNode element) {
        super(element, new TextRange(0, element.getText().length()), false);
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean b) {
        if (isChildOf(ModuleInstantiationPsiNode.class)) {
            if (isChildOf(ModuleIdentifierPsiNode.class)) {
                return getAllVerilogFiles(myElement.getProject()).stream()
                        .flatMap(verilogFile -> verilogFile.getAvailableNamedElements().stream())
                        .filter(psiNamedElement -> psiNamedElement instanceof ModuleDeclarationPsiNode)
                        .filter(psiNamedElement ->
                                Objects.equals(
                                        psiNamedElement.getName(),
                                        myElement.getName()))
                        .map(PsiElementResolveResult::new)
                        .toArray(ResolveResult[]::new);
            }
            if (isChildOf(PortIdentifierPsiNode.class)) {
                ModuleInstantiationPsiNode moduleInstantiationPsiNode =
                        PsiTreeUtil.getParentOfType(myElement, ModuleInstantiationPsiNode.class);
                if (moduleInstantiationPsiNode == null) {
                    return new ResolveResult[0];
                }
                SimpleIdentifierPsiLeafNode simpleIdentifier =
                        PsiTreeUtil.findChildOfType(
                                PsiTreeUtil.findChildOfType(moduleInstantiationPsiNode, ModuleIdentifierPsiNode.class),
                                SimpleIdentifierPsiLeafNode.class
                        );
                if (simpleIdentifier == null) {
                    return new ResolveResult[0];
                }
                return Arrays.stream(((PsiReferenceBase.Poly) simpleIdentifier.getReference()).multiResolve(false))
                        .map(ResolveResult::getElement)
                        .filter(psiElement -> psiElement instanceof ModuleDeclarationPsiNode)
                        .map(psiElement -> (ModuleDeclarationPsiNode) psiElement)
                        .flatMap(moduleDeclarationPsiNode ->
                                moduleDeclarationPsiNode.getAvailableNamedElements().stream())
                        .filter(psiNamedElement -> Objects.equals(
                                psiNamedElement.getName(),
                                myElement.getName()
                        ))
                        .map(PsiElementResolveResult::new)
                        .toArray(ResolveResult[]::new);
            }
        }
        ModuleDeclarationPsiNode moduleDeclarationPsiNode =
                PsiTreeUtil.getParentOfType(myElement, ModuleDeclarationPsiNode.class);
        if (moduleDeclarationPsiNode == null) {
            return new ResolveResult[0];
        }
        return moduleDeclarationPsiNode.getAvailableNamedElements().stream()
                .filter(psiNamedElement -> Objects.equals(
                        psiNamedElement.getName(),
                        myElement.getName()
                ))
                .map(PsiElementResolveResult::new)
                .toArray(ResolveResult[]::new);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }


    private boolean isChildOf(Class<? extends ANTLRPsiNode> aClass) {
        return PsiTreeUtil.getParentOfType(myElement, aClass) != null;
    }

}
