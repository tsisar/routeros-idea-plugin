package com.mikrotik.routeros

import com.intellij.lexer.LexerBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

class RouterOSLexer : LexerBase() {
    private var buffer: CharSequence = ""
    private var bufferEnd = 0
    private var tokenStart = 0
    private var tokenEnd = 0
    private var tokenType: IElementType? = null
    private var atLineStart = true
    private var inPathLine = false

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        this.buffer = buffer
        this.bufferEnd = endOffset
        this.tokenStart = startOffset
        this.tokenEnd = startOffset
        this.atLineStart = true
        this.inPathLine = false
        advance()
    }

    override fun getState(): Int = 0
    override fun getTokenType(): IElementType? = tokenType
    override fun getTokenStart(): Int = tokenStart
    override fun getTokenEnd(): Int = tokenEnd
    override fun getBufferSequence(): CharSequence = buffer
    override fun getBufferEnd(): Int = bufferEnd

    override fun advance() {
        tokenStart = tokenEnd
        if (tokenStart >= bufferEnd) {
            tokenType = null
            return
        }
        val wasAtLineStart = atLineStart
        val c = buffer[tokenStart]
        when {
            isWhitespace(c)                                              -> consumeWhitespace()
            c == '#'                                                     -> consumeLineComment()
            c == '"'                                                     -> consumeString()
            c == '$'                                                     -> consumeVariable()
            c == '/' && tokenStart + 1 < bufferEnd
                    && isIdStart(buffer[tokenStart + 1])                 -> consumePath(wasAtLineStart)
            c == '{'                                                     -> emit(1, RouterOSTokenTypes.LBRACE)
            c == '}'                                                     -> emit(1, RouterOSTokenTypes.RBRACE)
            c == '['                                                     -> emit(1, RouterOSTokenTypes.LBRACKET)
            c == ']'                                                     -> emit(1, RouterOSTokenTypes.RBRACKET)
            c == '('                                                     -> emit(1, RouterOSTokenTypes.LPAREN)
            c == ')'                                                     -> emit(1, RouterOSTokenTypes.RPAREN)
            isOperator(c)                                                -> emit(1, RouterOSTokenTypes.OPERATOR)
            isDigit(c)                                                   -> consumeNumberOrWord(wasAtLineStart)
            isIdStart(c)                                                 -> consumeWord(wasAtLineStart)
            else                                                         -> emit(1, TokenType.BAD_CHARACTER)
        }
        if (tokenType != TokenType.WHITE_SPACE && tokenType != RouterOSTokenTypes.COMMENT) {
            atLineStart = false
        }
    }

    private fun emit(len: Int, type: IElementType) {
        tokenEnd = tokenStart + len
        tokenType = type
    }

    private fun isWhitespace(c: Char) = c == ' ' || c == '\t' || c == '\n' || c == '\r'
    private fun isDigit(c: Char) = c in '0'..'9'
    private fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z' || c == '_'
    private fun isIdStart(c: Char) = isLetter(c)
    private fun isIdPart(c: Char) = isLetter(c) || isDigit(c) || c == '-'
    private fun isOperator(c: Char) =
        c == '=' || c == ',' || c == ';' || c == ':' || c == '.' ||
        c == '+' || c == '*' || c == '<' || c == '>' || c == '!' ||
        c == '|' || c == '&' || c == '?' || c == '~' || c == '/'

    private fun consumeWhitespace() {
        var i = tokenStart
        var hasNewline = false
        while (i < bufferEnd && isWhitespace(buffer[i])) {
            if (buffer[i] == '\n') hasNewline = true
            i++
        }
        tokenEnd = i
        tokenType = TokenType.WHITE_SPACE
        if (hasNewline) {
            atLineStart = true
            inPathLine = false
        }
    }

    private fun consumeLineComment() {
        var i = tokenStart
        while (i < bufferEnd && buffer[i] != '\n') i++
        tokenEnd = i
        tokenType = RouterOSTokenTypes.COMMENT
    }

    private fun consumeString() {
        var i = tokenStart + 1
        while (i < bufferEnd && buffer[i] != '"' && buffer[i] != '\n') {
            i += if (buffer[i] == '\\' && i + 1 < bufferEnd) 2 else 1
        }
        if (i < bufferEnd && buffer[i] == '"') i++
        tokenEnd = i
        tokenType = RouterOSTokenTypes.STRING
    }

    private fun consumeVariable() {
        var i = tokenStart + 1
        while (i < bufferEnd && isIdPart(buffer[i])) i++
        tokenEnd = i
        tokenType = RouterOSTokenTypes.VARIABLE
    }

    private fun consumePath(wasAtLineStart: Boolean) {
        var i = tokenStart + 1
        while (i < bufferEnd && isIdPart(buffer[i])) i++
        tokenEnd = i
        tokenType = RouterOSTokenTypes.PATH
        if (wasAtLineStart) inPathLine = true
    }

    private fun consumeNumberOrWord(wasAtLineStart: Boolean) {
        var i = tokenStart
        var hasLetter = false
        while (i < bufferEnd) {
            val ch = buffer[i]
            when {
                isDigit(ch) || ch == '.'            -> i++
                isLetter(ch) || ch == '-'           -> { hasLetter = true; i++ }
                else                                -> break
            }
        }
        tokenEnd = i
        tokenType = if (!hasLetter) RouterOSTokenTypes.NUMBER else classifyWord(wasAtLineStart)
    }

    private fun consumeWord(wasAtLineStart: Boolean) {
        var i = tokenStart
        while (i < bufferEnd && isIdPart(buffer[i])) i++
        tokenEnd = i
        tokenType = classifyWord(wasAtLineStart)
    }

    private fun classifyWord(wasAtLineStart: Boolean): IElementType {
        if (inPathLine) return RouterOSTokenTypes.PATH
        if (wasAtLineStart) {
            val word = buffer.subSequence(tokenStart, tokenEnd).toString()
            if (RouterOSKeywords.commands.contains(word)) return RouterOSTokenTypes.COMMAND
        }
        if (isImmediatelyFollowedByEquals()) return RouterOSTokenTypes.PROPERTY
        return RouterOSTokenTypes.IDENTIFIER
    }

    private fun isImmediatelyFollowedByEquals(): Boolean =
        tokenEnd < bufferEnd && buffer[tokenEnd] == '='
}