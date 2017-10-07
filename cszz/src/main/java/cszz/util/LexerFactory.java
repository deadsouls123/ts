
package cszz.util;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import cszz.antlr.CszzLexer;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
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
