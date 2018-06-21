package com.verilang.psi.references

import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.ResolveResult
import com.verilang.psi.factory.nodes.ModuleDeclarationPsiNode
import com.verilang.psi.factory.nodes.ModuleInstantiationPsiNode
import com.verilang.utils.getAllVerilogFiles

class ModuleInstantiationReference(element: ModuleInstantiationPsiNode)
    : PsiReferenceBase.Poly<ModuleInstantiationPsiNode>(
        element,
        element.getHoldPsiNodeRelativeTextRange(),
        true
) {

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        return getAllVerilogFiles(myElement.project)
                .flatMap { it.availableNamedElements }
                .filterIsInstance(ModuleDeclarationPsiNode::class.java)
                .filter { it.name == myElement.getHoldPsiNode()?.name }
                .map { PsiElementResolveResult(it) }
                .toTypedArray()
    }

    override fun getVariants(): Array<Any> {
        return emptyArray()
    }

}