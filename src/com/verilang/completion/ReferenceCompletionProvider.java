package com.verilang.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.verilang.VerilogFileType;
import com.verilang.psi.TypedDeclaration;
import com.verilang.psi.factory.nodes.ModuleDeclarationPsiNode;
import com.verilang.psi.factory.nodes.ModuleIdentifierPsiNode;
import com.verilang.psi.factory.nodes.ModuleInstantiationPsiNode;
import com.verilang.psi.factory.nodes.SimpleIdentifierPsiLeafNode;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;


// TODO it is too dirty now, refactoring needed
class ReferenceCompletionProvider extends CompletionProvider<CompletionParameters> {
    
    @Override
    protected void addCompletions(@NotNull CompletionParameters completionParameters,
                                  ProcessingContext processingContext,
                                  @NotNull CompletionResultSet completionResultSet) {
        PsiElement currentElement = completionParameters.getOriginalPosition();
        ModuleInstantiationPsiNode moduleInstantiationPsiNode =
                PsiTreeUtil.getParentOfType(currentElement, ModuleInstantiationPsiNode.class);
        if (moduleInstantiationPsiNode != null) {
            if (currentElement.getPrevSibling() != null
                    && currentElement.getPrevSibling().getText().equals(".")) {
                ModuleIdentifierPsiNode moduleIdentifierPsiNode =
                        PsiTreeUtil.getChildOfType(
                                moduleInstantiationPsiNode,
                                ModuleIdentifierPsiNode.class);
                if (moduleIdentifierPsiNode == null) {
                    return;
                }
                SimpleIdentifierPsiLeafNode simpleIdentifierPsiLeafNode =
                        (SimpleIdentifierPsiLeafNode) moduleIdentifierPsiNode.getNameIdentifier();
                if (simpleIdentifierPsiLeafNode == null) {
                    return;
                }
                Arrays.stream(
                        ((PsiReferenceBase.Poly)
                                simpleIdentifierPsiLeafNode.getReference()).multiResolve(true))
                        .map(ResolveResult::getElement)
                        .filter(Objects::nonNull)
                        .map(psiElement ->
                                (ModuleDeclarationPsiNode) psiElement)
                        .flatMap(moduleDeclarationPsiNode ->
                                moduleDeclarationPsiNode.getAvailableNamedElements().stream())
                        .forEach(psiNamedElement ->
                                completionResultSet.addElement(
                                        buildLookupElement(psiNamedElement, true)));
                return;
            }
        }

        ModuleDeclarationPsiNode moduleDeclarationPsiNode =
                PsiTreeUtil.getParentOfType(
                        completionParameters.getOriginalPosition(),
                        ModuleDeclarationPsiNode.class
                );
        if (moduleDeclarationPsiNode == null) {
            return;
        }
        moduleDeclarationPsiNode.getAvailableNamedElements()
                .forEach(psiNamedElement ->
                        completionResultSet.addElement(
                                buildLookupElement(psiNamedElement, false)));
    }

    private LookupElement buildLookupElement(PsiNamedElement psiElement, boolean withModuleName) {
        TypedDeclaration typedDeclarationParent =
                PsiTreeUtil.getParentOfType(
                        psiElement,
                        TypedDeclaration.class
                );
        ModuleDeclarationPsiNode nodeModule =
                PsiTreeUtil.getParentOfType(psiElement, ModuleDeclarationPsiNode.class);
        String typeText = "";
        String tailText = "";

        if (withModuleName && nodeModule != null && nodeModule.getName() != null) {
            tailText += " (" + nodeModule.getName() +
                    " in \"" + nodeModule.getContainingFile().getName() + "\")";
        }

        if (typedDeclarationParent != null) {
            typeText += typedDeclarationParent.getTypeText();
        }

        return LookupElementBuilder.create(psiElement)
                .withIcon(VerilogFileType.INSTANCE.getIcon())
                .withTypeText(typeText)
                .withTailText(tailText);
    }

}
