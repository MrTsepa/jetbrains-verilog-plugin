package com.verilang.annotation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.util.PsiTreeUtil
import com.verilang.psi.factory.nodes.*

class VerilogAnnotator : Annotator {

    override fun annotate(psiElement: PsiElement, annotationHolder: AnnotationHolder) {

        val reference = when (psiElement) {
            is ModuleIdentifierPsiNode -> PsiTreeUtil
                    .getParentOfType(psiElement, ModuleInstantiationPsiNode::class.java)
                    ?.reference ?: return
            is PortIdentifierPsiNode -> PsiTreeUtil
                    .getParentOfType(psiElement, NamedPortConnectionPsiNode::class.java)
                    ?.reference ?: return
            else -> psiElement.reference ?: return
        }

        when (reference) {
            is PsiReferenceBase.Poly<*> -> {
                if (reference.multiResolve(true).isEmpty()) {
                    annotateUnresolvedReference(psiElement, annotationHolder)
                }
            }
            is PsiReferenceBase<*> -> {
                if (reference.resolve() == null) {
                    annotateUnresolvedReference(psiElement, annotationHolder)
                }
            }
        }
    }

    private fun annotateUnresolvedReference(psiElement: PsiElement, annotationHolder: AnnotationHolder) {
        if (psiElement is ReferenceHolder<*>) {
            annotationHolder.createErrorAnnotation(
                    psiElement.getHoldPsiNode() ?: psiElement, "Unresolved reference"
            )
        } else {
            annotationHolder.createErrorAnnotation(
                    psiElement, "Unresolved reference"
            )
        }
    }

}