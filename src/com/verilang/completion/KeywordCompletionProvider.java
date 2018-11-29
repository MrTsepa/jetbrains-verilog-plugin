package com.verilang.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class KeywordCompletionProvider extends CompletionProvider<CompletionParameters> {

    private String[] keywords;
    private Icon icon;

    public KeywordCompletionProvider(String[] keywords) {
        this.keywords = keywords;
    }

    public KeywordCompletionProvider(String[] keywords, Icon icon) {
        this.keywords = keywords;
        this.icon = icon;
    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters completionParameters,
                                  @NotNull ProcessingContext processingContext,
                                  @NotNull CompletionResultSet completionResultSet) {
        for (String keyword : keywords) {
            completionResultSet.addElement(
                    LookupElementBuilder.create(keyword)
                            .bold()
                            .withIcon(icon)
            );
        }

    }

}
