package com.verilang.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import com.verilang.VerilogFileType
import com.verilang.psi.TypedDeclaration
import com.verilang.psi.factory.nodes.ModuleDeclarationPsiNode

class LocalReferenceCompletionProvider : CompletionProvider<CompletionParameters>() {

    override fun addCompletions(parameters: CompletionParameters,
                                context: ProcessingContext,
                                completionResultSet: CompletionResultSet) {
        val moduleDeclarationPsiNode = PsiTreeUtil.getParentOfType(
                parameters.originalPosition,
                ModuleDeclarationPsiNode::class.java
        )
        moduleDeclarationPsiNode
                ?.availableNamedElements
                ?.forEach { completionResultSet.addElement(
                        buildLookupElement(it)) }
    }

    private fun buildLookupElement(psiElement: PsiNamedElement) : LookupElement {
        val typedDeclarationParent =
                PsiTreeUtil.getParentOfType(
                        psiElement,
                        TypedDeclaration::class.java)
        val typeText = typedDeclarationParent?.typeText ?: ""
        return LookupElementBuilder.create(psiElement)
                .withIcon(VerilogFileType.INSTANCE.icon)
                .withTypeText(typeText)
    }

}