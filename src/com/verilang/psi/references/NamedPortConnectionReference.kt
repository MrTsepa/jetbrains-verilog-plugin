package com.verilang.psi.references

import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.ResolveResult
import com.intellij.psi.util.PsiTreeUtil
import com.verilang.psi.factory.nodes.*

class NamedPortConnectionReference(element: NamedPortConnectionPsiNode)
    : PsiReferenceBase.Poly<NamedPortConnectionPsiNode>(
        element,
        element.getPortIdentifierRelativeTextRange(),
        true
) {
    
    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val moduleInstantiationPsiNode = PsiTreeUtil
                .getParentOfType(myElement, ModuleInstantiationPsiNode::class.java)
                ?: return emptyArray()
        val simpleIdentifier = PsiTreeUtil
                .findChildOfType(
                        PsiTreeUtil.findChildOfType(moduleInstantiationPsiNode, ModuleIdentifierPsiNode::class.java),
                        SimpleIdentifierPsiLeafNode::class.java)
                ?: return emptyArray()
        return (simpleIdentifier.reference as? PsiReferenceBase.Poly<*> ?: return emptyArray())
                .multiResolve(incompleteCode)
                .map { it.element }
                .filterIsInstance(ModuleDeclarationPsiNode::class.java)
                .flatMap { it.availableNamedElements }
                .filter { it.name == myElement.getPortIdentifier()?.name }
                .map { PsiElementResolveResult(it) }
                .toTypedArray()
    }

    override fun getVariants(): Array<Any> {
        return emptyArray()
    }

}