package com.verilang.psi.references

import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.verilang.psi.factory.nodes.*


class HierarchicalIdentifierReference(element: HierarchicalIdentifierPsiNode) :
        PsiReferenceBase<HierarchicalIdentifierPsiNode>(
                element,
                element.getHoldPsiNodeRelativeTextRange()
        ) {

    override fun resolve(): PsiElement? {
        return (PsiTreeUtil
                .getParentOfType(
                        myElement,
                        ModuleDeclarationPsiNode::class.java
                ) ?: return null)
                .availableNamedElements
                .find { it.name == myElement.name }
    }


    override fun getVariants(): Array<Any> {
        return emptyArray()
    }

}