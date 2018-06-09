package com.verilang.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import com.verilang.VerilogFileType;
import com.verilang.VerilogKeywords;


public class VerilogCompletionContributor extends CompletionContributor {

    public VerilogCompletionContributor() {
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement(),
                new KeywordCompletionProvider(
                        VerilogKeywords.VERILOG_KEYWORDS,
                        VerilogFileType.INSTANCE.getIcon()
                )
        );

        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement(),
                new ReferenceCompletionProvider()
        );
    }

}
