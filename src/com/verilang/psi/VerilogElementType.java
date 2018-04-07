package com.verilang.psi;

import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;
import com.verilang.VerilogLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class VerilogElementType extends IElementType {

    public VerilogElementType(@NotNull @NonNls String debugName) {
        super(debugName, VerilogLanguage.INSTANCE);
    }

}
