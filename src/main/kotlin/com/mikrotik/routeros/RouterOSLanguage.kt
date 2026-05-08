package com.mikrotik.routeros

import com.intellij.lang.Language

object RouterOSLanguage : Language("RouterOS") {
    override fun isCaseSensitive(): Boolean = true
}
