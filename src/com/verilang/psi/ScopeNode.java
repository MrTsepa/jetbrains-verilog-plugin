package com.verilang.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;

import java.util.List;

public interface ScopeNode extends PsiElement {

    List<PsiNamedElement> getAvaileableNamedElements();

}
