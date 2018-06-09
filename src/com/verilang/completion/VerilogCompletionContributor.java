package com.verilang.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.verilang.VerilogFileType;
import com.verilang.VerilogKeywords;
import com.verilang.psi.factory.nodes.ModuleInstantiationPsiNode;
import com.verilang.psi.factory.nodes.StatementPsiNode;

import static com.intellij.patterns.StandardPatterns.*;


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
                and(
                        PlatformPatterns.psiElement().afterLeaf(";"),
                        not(PlatformPatterns.psiElement().inside(StatementPsiNode.class))
                ),
                new ModuleItemOuterReferenceCompletionProvider()
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
                not(moduleInstantiationOuterReferencePattern),
                new ReferenceCompletionProvider()
        );
    }

}
