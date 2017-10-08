package cszz.compiler;

import org.antlr.v4.runtime.CommonTokenStream;

import cszz.antlr.CszzLexer;
import cszz.antlr.CszzParser;
import cszz.compiler.codegen.Ast2Java;
import cszz.util.LexerFactory;
import cszz.util.TokenStreamFactory;
/**
 *
 * 
 */
public class DefaultCompileContext implements CompileContext{

    @Override
    public CszzLexer createLexer(CompilationUnit compilationUnit, String source) {
        return LexerFactory.createLexer(source);
    }

    @Override
    public CommonTokenStream createTokenStream(CompilationUnit compilationUnit, CszzLexer lexer) {
        return TokenStreamFactory.createTokenStream(lexer);
    }

    @Override
    public CszzParser createParser(CompilationUnit compilationUnit, CommonTokenStream tokenStream) {
        return new CszzParser(tokenStream);
    }

    @Override
    public AstBuilder createAstBuilder(CompilationUnit compilationUnit, CszzParser parser) {
        return new AstBuilder(compilationUnit, parser);
    }

    @Override
    public CodeGenerator createCodeGenerator(CompilationUnit compilationUnit) {
        return new Ast2Java();
    }

    @Override
    public SemanticAnalyzer createSemanticAnalyzer(CompilationUnit compilationUnit, AstLoader astLoader) {
        return new SemanticAnalyzer(compilationUnit, astLoader);
    }

    @Override
    public AstLoader getAstLoader() {
        return AstLoader.BASE_AST_LOADER;
    }

    @Override
    public SourceLoader getSourceLoader() {
        return new SourceLoader() {
            @Override
            public CszzSource loadSource(String className) {
                return null;
            }
        };
    }

    @Override
    public DiagnosisHandler getDiagnosisHandler() {
        return StandardDiagnosisHandler.INSTANCE;
    }

    @Override
    public void stopCompile(int stopPhase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getCompilingPhase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
