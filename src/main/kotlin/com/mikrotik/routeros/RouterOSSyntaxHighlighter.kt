package com.mikrotik.routeros

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

class RouterOSSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer = RouterOSLexer()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> =
        when (tokenType) {
            RouterOSTokenTypes.COMMENT     -> COMMENT_KEYS
            RouterOSTokenTypes.STRING      -> STRING_KEYS
            RouterOSTokenTypes.NUMBER      -> NUMBER_KEYS
            RouterOSTokenTypes.VARIABLE    -> VARIABLE_KEYS
            RouterOSTokenTypes.PATH        -> PATH_KEYS
            RouterOSTokenTypes.COMMAND     -> COMMAND_KEYS
            RouterOSTokenTypes.PROPERTY    -> PROPERTY_KEYS
            RouterOSTokenTypes.IDENTIFIER  -> IDENTIFIER_KEYS
            RouterOSTokenTypes.OPERATOR    -> OPERATOR_KEYS
            RouterOSTokenTypes.LBRACE,
            RouterOSTokenTypes.RBRACE      -> BRACES_KEYS
            RouterOSTokenTypes.LBRACKET,
            RouterOSTokenTypes.RBRACKET    -> BRACKETS_KEYS
            RouterOSTokenTypes.LPAREN,
            RouterOSTokenTypes.RPAREN      -> PARENTHESES_KEYS
            TokenType.BAD_CHARACTER        -> BAD_CHAR_KEYS
            else                           -> EMPTY_KEYS
        }

    companion object {
        val COMMENT     = key("ROUTEROS_COMMENT",    DefaultLanguageHighlighterColors.LINE_COMMENT)
        val STRING      = key("ROUTEROS_STRING",     DefaultLanguageHighlighterColors.STRING)
        val NUMBER      = key("ROUTEROS_NUMBER",     DefaultLanguageHighlighterColors.NUMBER)
        val VARIABLE    = key("ROUTEROS_VARIABLE",   DefaultLanguageHighlighterColors.LOCAL_VARIABLE)
        val PATH        = key("ROUTEROS_PATH",       DefaultLanguageHighlighterColors.CLASS_REFERENCE)
        val COMMAND     = key("ROUTEROS_COMMAND",    DefaultLanguageHighlighterColors.KEYWORD)
        val PROPERTY    = key("ROUTEROS_PROPERTY",   DefaultLanguageHighlighterColors.INSTANCE_FIELD)
        val IDENTIFIER  = key("ROUTEROS_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER)
        val OPERATOR    = key("ROUTEROS_OPERATOR",   DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val BRACES      = key("ROUTEROS_BRACES",     DefaultLanguageHighlighterColors.BRACES)
        val BRACKETS    = key("ROUTEROS_BRACKETS",   DefaultLanguageHighlighterColors.BRACKETS)
        val PARENTHESES = key("ROUTEROS_PAREN",      DefaultLanguageHighlighterColors.PARENTHESES)
        val BAD_CHAR    = key("ROUTEROS_BAD_CHAR",   com.intellij.openapi.editor.HighlighterColors.BAD_CHARACTER)

        private val COMMENT_KEYS     = arrayOf(COMMENT)
        private val STRING_KEYS      = arrayOf(STRING)
        private val NUMBER_KEYS      = arrayOf(NUMBER)
        private val VARIABLE_KEYS    = arrayOf(VARIABLE)
        private val PATH_KEYS        = arrayOf(PATH)
        private val COMMAND_KEYS     = arrayOf(COMMAND)
        private val PROPERTY_KEYS    = arrayOf(PROPERTY)
        private val IDENTIFIER_KEYS  = arrayOf(IDENTIFIER)
        private val OPERATOR_KEYS    = arrayOf(OPERATOR)
        private val BRACES_KEYS      = arrayOf(BRACES)
        private val BRACKETS_KEYS    = arrayOf(BRACKETS)
        private val PARENTHESES_KEYS = arrayOf(PARENTHESES)
        private val BAD_CHAR_KEYS    = arrayOf(BAD_CHAR)
        private val EMPTY_KEYS       = emptyArray<TextAttributesKey>()

        private fun key(name: String, fallback: TextAttributesKey): TextAttributesKey =
            TextAttributesKey.createTextAttributesKey(name, fallback)
    }
}
