package com.verilang.psi.factory.nodes;

import com.intellij.psi.PsiReference;
import com.verilang.VerilogLexer;
import com.verilang.psi.SimpleIdentifierReference;
import com.verilang.psi.factory.VerilogPsiLeafNodeFactory;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiLeafNode;
import org.jetbrains.annotations.NotNull;

public class SimpleIdentifierPsiLeafNode extends ANTLRPsiLeafNode {

    public SimpleIdentifierPsiLeafNode(CharSequence text) {
        super(VerilogPsiLeafNodeFactory.getTokenIElementType(VerilogLexer.Simple_identifier), text);
    }

    @Override
    public String getName() {
        return this.getText();
    }

    @NotNull
    @Override
    public PsiReference getReference() {
        return new SimpleIdentifierReference(this);
    }
}
