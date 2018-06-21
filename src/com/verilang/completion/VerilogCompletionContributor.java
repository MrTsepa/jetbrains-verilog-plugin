package com.verilang.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.verilang.VerilogFileType;
import com.verilang.VerilogKeywords;
import com.verilang.psi.factory.nodes.NamedPortConnectionPsiNode;
import com.verilang.psi.factory.nodes.PortIdentifierPsiNode;
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

        ElementPattern<PsiElement> namedPortConnectionPattern =
                PlatformPatterns.psiElement()
                        .inside(NamedPortConnectionPsiNode.class)
                        .inside(PortIdentifierPsiNode.class);

        extend(
                CompletionType.BASIC,
                namedPortConnectionPattern,
                new NamedPortConnectionCompletionProvider()
        );

        extend(
                CompletionType.BASIC,
                not(namedPortConnectionPattern),
                new LocalReferenceCompletionProvider()
        );
    }

}
