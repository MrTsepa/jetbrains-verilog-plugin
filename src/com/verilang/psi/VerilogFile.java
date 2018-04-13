package com.verilang.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.verilang.VerilogFileType;
import com.verilang.VerilogLanguage;
import com.verilang.psi.factory.nodes.ModuleDeclarationPsiNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;

public class VerilogFile extends PsiFileBase implements ScopeNode {

    public VerilogFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, VerilogLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return VerilogFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Verilog";
    }

    @Nullable
    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }

    @Override
    public Collection<PsiNamedElement> getAvailableNamedElements() {
        return PsiTreeUtil.findChildrenOfType(this, ModuleDeclarationPsiNode.class);
    }
}
