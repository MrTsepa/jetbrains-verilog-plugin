package com.verilang.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.verilang.VerilogFileType;
import com.verilang.VerilogKeywords;
import com.verilang.psi.TypedDeclaration;
import com.verilang.psi.factory.nodes.ModuleDeclarationPsiNode;
import com.verilang.psi.factory.nodes.ModuleIdentifierPsiNode;
import com.verilang.psi.factory.nodes.ModuleInstantiationPsiNode;
import com.verilang.psi.factory.nodes.SimpleIdentifierPsiLeafNode;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;


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
                PlatformPatterns
                        .psiElement(),
                new VerilogCompletionProvider(
                        VerilogFileType.INSTANCE.getIcon()
                )
        );
    }



}
