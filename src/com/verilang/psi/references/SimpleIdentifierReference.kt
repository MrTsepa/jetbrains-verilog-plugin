package com.verilang.psi.references

import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.verilang.psi.factory.nodes.*
import com.verilang.utils.getAllVerilogFiles
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode


class SimpleIdentifierReference(element: SimpleIdentifierPsiLeafNode) :
        PsiReferenceBase.Poly<SimpleIdentifierPsiLeafNode>(element, TextRange(0, element.text.length), false) {

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        if (isChildOf(ModuleInstantiationPsiNode::class.java)) {
            if (isChildOf(ModuleIdentifierPsiNode::class.java)) {
                return resolveModuleNames(incompleteCode)
            }
            if (isChildOf(PortIdentifierPsiNode::class.java)) {
                // Do nothing
            }
        }
        return resolveLocally(incompleteCode)
    }

    private fun resolveLocally(incompleteCode: Boolean): Array<ResolveResult> {
        return (PsiTreeUtil
                .getParentOfType(
                        myElement,
                        ModuleDeclarationPsiNode::class.java
                ) ?: return emptyArray())
                .availableNamedElements
                .filter { it.name == myElement.name }
                .map { PsiElementResolveResult(it) }
                .toTypedArray()
    }

    private fun resolveModuleNames(incompleteCode: Boolean): Array<ResolveResult> {
        return getAllVerilogFiles(myElement.project)
                .flatMap { it.availableNamedElements }
                .filterIsInstance(ModuleDeclarationPsiNode::class.java)
                .filter { it.name == myElement.name }
                .map { PsiElementResolveResult(it) }
                .toTypedArray()
    }

    private fun isChildOf(aClass: Class<out ANTLRPsiNode>): Boolean {
        return PsiTreeUtil.getParentOfType(myElement, aClass) != null
    }

    override fun getVariants(): Array<Any> {
        return emptyArray()
    }

}