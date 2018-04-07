package com.verilang;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.FlexLexer;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;

public class VerilogLexerAdapter extends FlexAdapter {

    public VerilogLexerAdapter() {
        super(new VerilogLexer((Reader) null));
    }

}
