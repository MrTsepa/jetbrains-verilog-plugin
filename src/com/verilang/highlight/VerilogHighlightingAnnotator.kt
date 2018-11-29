package com.verilang.highlight

import com.intellij.ide.highlighter.JavaHighlightingColors
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.psi.PsiElement
import com.verilang.psi.factory.nodes.DirectivePsiNode


class VerilogHighlightingAnnotator : Annotator {

    companion object {
        val DIRECTIVE: TextAttributesKey =
                createTextAttributesKey(
                        "VERILOG_DIRECTIVE",
                        JavaHighlightingColors.ANNOTATION_NAME_ATTRIBUTES
                )
    }

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is DirectivePsiNode) {
            val annotation = holder.createInfoAnnotation(element.node, "")
            annotation.textAttributes = DIRECTIVE
        }
    }

}
