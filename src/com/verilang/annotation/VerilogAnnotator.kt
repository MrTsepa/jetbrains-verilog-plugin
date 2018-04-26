package com.verilang.annotation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.util.PsiTreeUtil
import com.verilang.psi.factory.nodes.HierarchicalIdentifierPsiNode
import com.verilang.psi.factory.nodes.SimpleIdentifierPsiLeafNode

class VerilogAnnotator : Annotator {

    override fun annotate(psiElement: PsiElement, annotationHolder: AnnotationHolder) {
        when (psiElement) {
            is HierarchicalIdentifierPsiNode -> {
                PsiTreeUtil.findChildrenOfType(
                        psiElement,
                        SimpleIdentifierPsiLeafNode::class.java
                ).filter { simpleIdentifierPsiLeafNode ->
                    val reference = simpleIdentifierPsiLeafNode.reference
                    if (reference is PsiReferenceBase.Poly<*>) {
                        return@filter reference.multiResolve(true).isEmpty()
                    } else {
                        return@filter reference.resolve() == null
                    }
                }.forEach { simpleIdentifierPsiLeafNode ->
                    annotationHolder.createErrorAnnotation(
                            simpleIdentifierPsiLeafNode.psi, "Unresolved reference"
                    )
                }
            }
        }
    }

}