package com.verilang.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.*;
import com.intellij.psi.PsiElement;
import com.verilang.VerilogFileType;
import com.verilang.VerilogKeywords;
import com.verilang.psi.factory.nodes.ModuleInstantiationPsiNode;


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

        ElementPattern<PsiElement> moduleInstantiationOuterReferencePattern =
                PlatformPatterns.psiElement()
                        .inside(ModuleInstantiationPsiNode.class)
                        .afterLeaf(".");

        extend(
                CompletionType.BASIC,
                moduleInstantiationOuterReferencePattern,
                new ModuleInstantiationOuterReferenceCompletionProvider()
        );

        extend(
                CompletionType.BASIC,
                StandardPatterns.not(
                        moduleInstantiationOuterReferencePattern
                ),
                new ReferenceCompletionProvider()
        );
    }

}
