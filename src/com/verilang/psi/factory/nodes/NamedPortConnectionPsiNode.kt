package com.verilang.psi.factory.nodes

import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiReference
import com.intellij.psi.util.PsiTreeUtil
import com.verilang.psi.references.NamedPortConnectionReference
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode

class NamedPortConnectionPsiNode(node: ASTNode)
    : ANTLRPsiNode(node), ReferenceHolder<PortIdentifierPsiNode> {

    override fun getReference(): PsiReference {
        return NamedPortConnectionReference(this)
    }

    public override fun getHoldPsiNode(): PortIdentifierPsiNode? {
        return PsiTreeUtil
                .findChildOfType(this, PortIdentifierPsiNode::class.java)
    }

    public override fun getHoldPsiNodeRelativeTextRange(): TextRange? {
        return getHoldPsiNode()
                ?.textRange
                ?.shiftLeft(this.textOffset)
    }

}