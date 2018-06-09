package com.verilang.psi.factory.nodes

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.IncorrectOperationException
import com.verilang.VerilogLexer
import com.verilang.psi.ScopeNode
import com.verilang.psi.factory.VerilogPsiLeafNodeFactory
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode


class ModuleDeclarationPsiNode(node: ASTNode)
    : ANTLRPsiNode(node), PsiNameIdentifierOwner, ScopeNode {

    override fun getName(): String? {
        return nameIdentifier?.text
    }

    override fun getNameIdentifier(): PsiElement? {
        return PsiTreeUtil
                .findChildOfType(this, ModuleIdentifierPsiNode::class.java)
                ?.firstChild
    }

    override fun getTextOffset(): Int {
        return PsiTreeUtil
                .findChildOfType(this, ModuleIdentifierPsiNode::class.java)
                ?.startOffsetInParent ?: 0
    }

    @Throws(IncorrectOperationException::class)
    override fun setName(s: String): PsiElement? {
        return this.nameIdentifier?.replace(
                VerilogPsiLeafNodeFactory.create(VerilogLexer.Simple_identifier, s))
    }

    override fun getAvailableNamedElements(): Collection<PsiNamedElement> {
        return listOf(
                PsiTreeUtil.findChildrenOfType(this, NetIdentifierPsiNode::class.java),
                PsiTreeUtil.findChildrenOfType(this, ParameterIdentifierPsiNode::class.java),
                PsiTreeUtil.findChildrenOfType(this, PortIdentifierPsiNode::class.java)
                        .filter {
                            PsiTreeUtil.getParentOfType(it, ModuleInstantiationPsiNode::class.java) == null
                        },
                PsiTreeUtil.findChildrenOfType(this, VariableIdentifierPsiNode::class.java)
        )
                .flatten()
                .filterIsInstance(PsiNamedElement::class.java)
    }
}
