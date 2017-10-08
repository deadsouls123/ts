package cszz.compiler;

import org.antlr.v4.runtime.CommonTokenStream;

import cszz.antlr.CszzLexer;
import cszz.antlr.CszzParser;
/**
 *
 * 
 */
public class CompileContextProxy implements CompileContext{
    
    private CompileContext config;

    public CompileContextProxy(CompileContext config) {
        this.config = config;
    }

    @Override
    public CszzLexer createLexer(CompilationUnit compilationUnit, String source) {
        return config.createLexer(compilationUnit, source);
    }

    @Override
    public CommonTokenStream createTokenStream(CompilationUnit compilationUnit, CszzLexer lexer) {
        return config.createTokenStream(compilationUnit, lexer);
    }

    @Override
    public CszzParser createParser(CompilationUnit compilationUnit, CommonTokenStream tokenStream) {
        return config.createParser(compilationUnit, tokenStream);
    }

    @Override
    public AstBuilder createAstBuilder(CompilationUnit compilationUnit, CszzParser parser) {
        return config.createAstBuilder(compilationUnit, parser);
    }

    @Override
    public CodeGenerator createCodeGenerator(CompilationUnit compilationUnit) {
        return config.createCodeGenerator(compilationUnit);
    }

    @Override
    public SemanticAnalyzer createSemanticAnalyzer(CompilationUnit compilationUnit, AstLoader astLoader) {
        return config.createSemanticAnalyzer(compilationUnit, astLoader);
    }

    @Override
    public AstLoader getAstLoader() {
        return config.getAstLoader();
    }

    @Override
    public SourceLoader getSourceLoader() {
        return config.getSourceLoader();
    }

    @Override
    public DiagnosisHandler getDiagnosisHandler() {
        return config.getDiagnosisHandler();
    }

    @Override
    public void stopCompile(int stopPhase) {
        config.stopCompile(stopPhase);
    }

    @Override
    public int getCompilingPhase() {
        return config.getCompilingPhase();
    }


    
}
