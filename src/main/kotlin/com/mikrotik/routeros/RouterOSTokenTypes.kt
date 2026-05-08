package com.mikrotik.routeros

import com.intellij.psi.tree.IElementType

class RouterOSTokenType(debugName: String) : IElementType(debugName, RouterOSLanguage)

object RouterOSTokenTypes {
    @JvmField val COMMENT     = RouterOSTokenType("COMMENT")
    @JvmField val STRING      = RouterOSTokenType("STRING")
    @JvmField val NUMBER      = RouterOSTokenType("NUMBER")
    @JvmField val VARIABLE    = RouterOSTokenType("VARIABLE")
    @JvmField val PATH        = RouterOSTokenType("PATH")
    @JvmField val COMMAND     = RouterOSTokenType("COMMAND")
    @JvmField val PROPERTY    = RouterOSTokenType("PROPERTY")
    @JvmField val IDENTIFIER  = RouterOSTokenType("IDENTIFIER")
    @JvmField val OPERATOR    = RouterOSTokenType("OPERATOR")
    @JvmField val LBRACE      = RouterOSTokenType("LBRACE")
    @JvmField val RBRACE      = RouterOSTokenType("RBRACE")
    @JvmField val LBRACKET    = RouterOSTokenType("LBRACKET")
    @JvmField val RBRACKET    = RouterOSTokenType("RBRACKET")
    @JvmField val LPAREN      = RouterOSTokenType("LPAREN")
    @JvmField val RPAREN      = RouterOSTokenType("RPAREN")
}
