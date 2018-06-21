package com.verilang.psi.factory.nodes

import com.verilang.VerilogLexer
import com.verilang.psi.factory.VerilogPsiLeafNodeFactory
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiLeafNode

class SimpleIdentifierPsiLeafNode(text: CharSequence)
    : ANTLRPsiLeafNode(VerilogPsiLeafNodeFactory.getTokenIElementType(VerilogLexer.Simple_identifier), text) {

    override fun getName(): String? {
        return this.text
    }

}
