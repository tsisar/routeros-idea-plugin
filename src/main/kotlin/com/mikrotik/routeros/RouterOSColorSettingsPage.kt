package com.mikrotik.routeros

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class RouterOSColorSettingsPage : ColorSettingsPage {
    override fun getIcon(): Icon = RouterOSIcons.FILE
    override fun getHighlighter(): SyntaxHighlighter = RouterOSSyntaxHighlighter()
    override fun getDisplayName(): String = "RouterOS"
    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = DESCRIPTORS
    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY
    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey>? = null

    override fun getDemoText(): String = """
        # firewall sample
        /ip firewall filter
        add chain=input action=accept connection-state=established,related,untracked comment="defconf: accept established"
        add chain=input action=drop  connection-state=invalid                       comment="defconf: drop invalid"

        :local count 0
        :foreach iface in=[/interface find] do={
            :set count (${'$'}count + 1)
        }
        :put "interfaces: ${'$'}count"
    """.trimIndent()

    companion object {
        private val DESCRIPTORS = arrayOf(
            AttributesDescriptor("Comment",          RouterOSSyntaxHighlighter.COMMENT),
            AttributesDescriptor("String",           RouterOSSyntaxHighlighter.STRING),
            AttributesDescriptor("Number",           RouterOSSyntaxHighlighter.NUMBER),
            AttributesDescriptor("Variable",         RouterOSSyntaxHighlighter.VARIABLE),
            AttributesDescriptor("Path",             RouterOSSyntaxHighlighter.PATH),
            AttributesDescriptor("Command",          RouterOSSyntaxHighlighter.COMMAND),
            AttributesDescriptor("Property",         RouterOSSyntaxHighlighter.PROPERTY),
            AttributesDescriptor("Identifier",       RouterOSSyntaxHighlighter.IDENTIFIER),
            AttributesDescriptor("Operator",         RouterOSSyntaxHighlighter.OPERATOR),
            AttributesDescriptor("Braces",           RouterOSSyntaxHighlighter.BRACES),
            AttributesDescriptor("Brackets",         RouterOSSyntaxHighlighter.BRACKETS),
            AttributesDescriptor("Parentheses",      RouterOSSyntaxHighlighter.PARENTHESES),
        )
    }
}
