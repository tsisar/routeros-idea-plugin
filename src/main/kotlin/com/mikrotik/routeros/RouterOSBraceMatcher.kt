package com.mikrotik.routeros

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType

class RouterOSBraceMatcher : PairedBraceMatcher {
    override fun getPairs(): Array<BracePair> = PAIRS
    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean = true
    override fun getCodeConstructStart(file: PsiFile?, openingBraceOffset: Int): Int = openingBraceOffset

    companion object {
        private val PAIRS = arrayOf(
            BracePair(RouterOSTokenTypes.LBRACE,   RouterOSTokenTypes.RBRACE,   true),
            BracePair(RouterOSTokenTypes.LBRACKET, RouterOSTokenTypes.RBRACKET, false),
            BracePair(RouterOSTokenTypes.LPAREN,   RouterOSTokenTypes.RPAREN,   false),
        )
    }
}
