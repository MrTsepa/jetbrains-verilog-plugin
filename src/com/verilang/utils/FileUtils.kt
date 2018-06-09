package com.verilang.utils

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.verilang.VerilogFileType
import com.verilang.psi.VerilogFile
import java.util.*
import java.util.stream.Collectors

fun getAllVerilogFiles(project: Project): List<VerilogFile> {
    return FileTypeIndex
            .getFiles(
                    VerilogFileType.INSTANCE,
                    GlobalSearchScope.allScope(project)
            ).stream()
            .map { PsiManager
                    .getInstance(project)
                    .findFile(it) }
            .filter { Objects.nonNull(it) }
            .filter { it is VerilogFile }
            .map { it as VerilogFile }
            .collect(Collectors.toList())
}