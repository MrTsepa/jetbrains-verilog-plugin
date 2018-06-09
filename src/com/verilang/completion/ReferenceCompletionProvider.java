package com.verilang.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.verilang.VerilogFileType;
import com.verilang.psi.TypedDeclaration;
import com.verilang.psi.factory.nodes.ModuleDeclarationPsiNode;
import org.jetbrains.annotations.NotNull;


class ReferenceCompletionProvider extends CompletionProvider<CompletionParameters> {

    @Override
    protected void addCompletions(@NotNull CompletionParameters completionParameters,
                                  ProcessingContext processingContext,
                                  @NotNull CompletionResultSet completionResultSet) {
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
                                buildLookupElement(psiNamedElement)));
    }

    private LookupElement buildLookupElement(PsiNamedElement psiElement) {
        TypedDeclaration typedDeclarationParent =
                PsiTreeUtil.getParentOfType(
                        psiElement,
                        TypedDeclaration.class
                );
        String typeText = "";

        if (typedDeclarationParent != null) {
            typeText += typedDeclarationParent.getTypeText();
        }

        return LookupElementBuilder.create(psiElement)
                .withIcon(VerilogFileType.INSTANCE.getIcon())
                .withTypeText(typeText);
    }

}
