package com.verilang.module

import com.intellij.openapi.projectRoots.*
import com.verilang.VerilogIcons
import org.jdom.Element

import java.io.*
import javax.swing.Icon

class IcarusVerilogSdkType : SdkType("iverilog") {

    companion object {
        const val DEFAULT_EXECUTABLE_NAME = "iverilog"
    }

    override fun suggestHomePath(): String? {
        return "/usr/bin"
    }

    override fun getPresentableName(): String {
        return "Icarus Verilog SDK"
    }

    override fun isValidSdkHome(s: String): Boolean {
        return isValidIcarusVerilog(getIcarusVerilogExecutable(s))
    }

    override fun suggestSdkName(currentSdkName: String?, sdkHome: String?): String {
        return "Icarus Verilog ${getVersionString(sdkHome)}"
    }

    override fun getIcon(): Icon {
        return VerilogIcons.ICON
    }

    override fun getIconForAddAction(): Icon {
        return VerilogIcons.ICON
    }

    override fun getVersionString(sdkHome: String?): String? {
        if (sdkHome == null) {
            return super.getVersionString(sdkHome)
        }
        return try {
            getIcarusVerilogVersion(getIcarusVerilogExecutable(sdkHome))
        } catch (ex: NotValidIcarusVerilog) {
            return super.getVersionString(sdkHome)
        }
    }

    override fun createAdditionalDataConfigurable(sdkModel: SdkModel, sdkModificator: SdkModificator): AdditionalDataConfigurable? {
        return null
    }

    override fun saveAdditionalData(sdkAdditionalData: SdkAdditionalData, element: Element) {

    }

    private fun isValidIcarusVerilog(filepath: String): Boolean {
        val command = "$filepath -V"
        val process: Process
        try {
            process = Runtime.getRuntime().exec(command)
        } catch (e: IOException) {
            return false
        }

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        try {
            val line = reader.readLine()
            if (line.startsWith("Icarus Verilog version")) {
                return true
            }
        } catch (ignored: IOException) {}
        return false
    }

    private fun getIcarusVerilogVersion(filepath: String): String {
        val command = "$filepath -V"
        val process: Process
        try {
            process = Runtime.getRuntime().exec(command)
        } catch (e: IOException) {
            throw NotValidIcarusVerilog()
        }

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        try {
            val line = reader.readLine()
            if (line.startsWith("Icarus Verilog version")) {
                return line.split(" ")[3]
            }
        } catch (ignored: Exception) {}
        throw NotValidIcarusVerilog()
    }

    private fun getIcarusVerilogExecutable(fileOrDirectory: String,
                                           executableName: String = DEFAULT_EXECUTABLE_NAME): String {
        val file = File(fileOrDirectory)
        return if (file.isDirectory) {
            File(file, executableName).absolutePath
        } else {
            file.absolutePath
        }
    }

    class NotValidIcarusVerilog : Exception()
}
