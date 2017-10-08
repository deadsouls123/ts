package cszz.util;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;

import cszz.antlr.CszzLexer;
/**
 *
 *  
 */
public class LexerFactory {
    
    public static CszzLexer createLexer(CharStream input){
        return new CszzLexer(input);
    }
    
    public static CszzLexer createLexer(String source){
        CszzLexer lexer = createLexer(new ANTLRInputStream(source));
        return lexer;
    }

}
