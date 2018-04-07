package com.verilang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.verilang.psi.VerilogTypes;
import com.intellij.psi.TokenType;

%%

%class VerilogLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment}

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?

%state YYINITIAL

%%

<YYINITIAL> "module"
    { return VerilogTypes.MODULE; }

<YYINITIAL> "endmodule"
    { return VerilogTypes.ENDMODULE; }

<YYINITIAL> {Comment}
    { yybegin(YYINITIAL); return VerilogTypes.COMMENT; }

<YYINITIAL> {InputCharacter}
    { yybegin(YYINITIAL); return VerilogTypes.INPUT_CHARACTER; }

.
    { return TokenType.BAD_CHARACTER; }