package com.verilang.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import com.verilang.VerilogFileType
import com.verilang.psi.factory.nodes.ModuleDeclarationPsiNode
import com.verilang.psi.factory.nodes.ModuleIdentifierPsiNode
import com.verilang.psi.factory.nodes.ModuleInstantiationPsiNode
import com.verilang.utils.getAllVerilogFiles

class ModuleItemOuterReferenceCompletionProvider : CompletionProvider<CompletionParameters>() {

    override fun addCompletions(parameters: CompletionParameters,
                                context: ProcessingContext?,
                                resultSet: CompletionResultSet) {
        val element = parameters.originalPosition
        val currentModuleDeclaration =
                PsiTreeUtil.getParentOfType(element, ModuleDeclarationPsiNode::class.java)
        val currentModuleInstantiationNames = PsiTreeUtil
                .findChildrenOfType(
                        currentModuleDeclaration,
                        ModuleInstantiationPsiNode::class.java)
                .map { PsiTreeUtil.findChildOfType(it, ModuleIdentifierPsiNode::class.java)?.name }
                .toSet()

        getAllVerilogFiles(element?.project ?: return).stream()
                .flatMap { it.availableNamedElements.stream() }
                .filter { it is ModuleDeclarationPsiNode }
                .filter { it.name !in currentModuleInstantiationNames }
                .filter { it != currentModuleDeclaration }
                .map { buildLookupElementForModuleDeclaration(it as ModuleDeclarationPsiNode) }
                .forEach { resultSet.addElement(it) }
    }

    private fun buildLookupElementForModuleDeclaration(
            moduleDeclarationPsiNode: ModuleDeclarationPsiNode) : LookupElement {
        return LookupElementBuilder
                .create(moduleDeclarationPsiNode)
                .withIcon(VerilogFileType.INSTANCE.icon)
    }

}