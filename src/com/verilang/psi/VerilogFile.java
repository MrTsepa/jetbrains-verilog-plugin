package com.verilang.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.verilang.VerilogFileType;
import com.verilang.VerilogLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class VerilogFile extends PsiFileBase {

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

}
