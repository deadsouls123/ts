
package cszz.util;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import cszz.antlr.CszzLexer;
import cszz.antlr.CszzParser;
import cszz.compiler.AstBuilder;
import cszz.compiler.CompilationUnit;
import cszz.compiler.Diagnosis;
import cszz.compiler.CszzSource;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
/**
 *
 * @author Kason Yang 
 */
public class AstBuilderFactory {
    
    public static AstBuilder createAstBuilder(CompilationUnit source,CszzLexer lexer){
        return AstBuilderFactory.createAstBuilder(
                source
                ,TokenStreamFactory.createTokenStream(lexer)
        );
    }
    
    public static AstBuilder createAstBuilder(CompilationUnit source){
        return AstBuilderFactory.createAstBuilder(
                source
                ,TokenStreamFactory.createTokenStream(source.getSource().getText())
        );
    }
        
    public static AstBuilder createAstBuilder(CompilationUnit source,TokenStream tokens){
        CszzParser p = new CszzParser(tokens);
        AstBuilder sp = new AstBuilder(source, p);
        p.setErrorHandler(new DefaultErrorStrategy() {

            @Override
            public void reportError(Parser recognizer, RecognitionException e) {
                String msg = AntlrErrorString.exceptionString(recognizer, e);
                Token end = e.getOffendingToken();
                Token start;
                RuleContext ctx = e.getCtx();
                if(ctx instanceof ParserRuleContext){
                    start = ((ParserRuleContext) ctx).getStart();
                }else{
                    start = end;
                }
                sp.getDiagnosisReporter().report(Diagnosis.Kind.ERROR, msg,start,end);
            }
        });
        return sp;
    }

}
