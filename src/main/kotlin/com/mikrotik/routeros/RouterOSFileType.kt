package com.mikrotik.routeros

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

class RouterOSFileType private constructor() : LanguageFileType(RouterOSLanguage) {
    override fun getName(): String = "RouterOS"
    override fun getDescription(): String = "MikroTik RouterOS script"
    override fun getDefaultExtension(): String = "rsc"
    override fun getIcon(): Icon = RouterOSIcons.FILE

    companion object {
        @JvmField
        val INSTANCE = RouterOSFileType()
    }
}
