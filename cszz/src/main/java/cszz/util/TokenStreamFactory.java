package cszz.util;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenSource;
/**
 *
 *  
 */
public class TokenStreamFactory {
    
    public static CommonTokenStream createTokenStream(String source) {
        return createTokenStream(LexerFactory.createLexer(source));
    }
    
    public static CommonTokenStream createTokenStream(TokenSource lexer) {
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        return tokens;
    }
    
}
