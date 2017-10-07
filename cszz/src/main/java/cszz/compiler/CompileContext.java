
package cszz.compiler;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import cszz.antlr.CszzLexer;
import cszz.antlr.CszzParser;
import org.antlr.v4.runtime.CommonTokenStream;
/**
 *
 *  
 */
public interface CompileContext {
    
    CszzLexer createLexer(CompilationUnit compilationUnit,String source);
    
    CommonTokenStream createTokenStream(CompilationUnit compilationUnit,CszzLexer lexer);
    
    CszzParser createParser(CompilationUnit compilationUnit,CommonTokenStream tokenStream);
    
    AstBuilder createAstBuilder(CompilationUnit compilationUnit,CszzParser parser);
    
    CodeGenerator createCodeGenerator(CompilationUnit compilationUnit);

    SemanticAnalyzer createSemanticAnalyzer(CompilationUnit compilationUnit, AstLoader astLoader);
    
    AstLoader getAstLoader();

    public SourceLoader getSourceLoader();
    
    public DiagnosisHandler getDiagnosisHandler();
    
    void stopCompile(int stopPhase);
    
    int getCompilingPhase();

}
