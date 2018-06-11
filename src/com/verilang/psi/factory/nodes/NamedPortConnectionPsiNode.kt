package com.verilang.psi.factory.nodes

import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiReference
import com.intellij.psi.util.PsiTreeUtil
import com.verilang.psi.references.NamedPortConnectionReference
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode

class NamedPortConnectionPsiNode(node: ASTNode) : ANTLRPsiNode(node) {

    override fun getReference(): PsiReference {
        return NamedPortConnectionReference(this)
    }

    public fun getPortIdentifier(): PortIdentifierPsiNode? {
        return PsiTreeUtil
                .findChildOfType(this, PortIdentifierPsiNode::class.java)
    }

    public fun getPortIdentifierRelativeTextRange(): TextRange? {
        return getPortIdentifier()
                ?.textRange
                ?.shiftLeft(this.textOffset)
    }

}