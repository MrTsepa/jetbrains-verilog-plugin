package com.verilang.psi;

import com.intellij.psi.tree.IElementType;
import com.verilang.VerilogLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class VerilogTokenType extends IElementType {

    public VerilogTokenType(@NotNull @NonNls String debugName) {
        super(debugName, VerilogLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "VerilogTokenType." + super.toString();
    }

}
