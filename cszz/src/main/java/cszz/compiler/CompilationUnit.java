package cszz.compiler;

import javax.annotation.Nonnull;
import cszz.antlr.CszzLexer;
import cszz.antlr.CszzParser;
import cszz.ast.ClassNode;
import org.antlr.v4.runtime.CommonTokenStream;
import static cszz.compiler.CompilePhase.*;

/**
 *
 *  
 */
public class CompilationUnit {

    private final CszzLexer lexer;
    private final CszzParser parser;
    private final AstBuilder astBuilder;
    
    private final SemanticAnalyzer semanticAnalyzer;
    
    private final CommonTokenStream tokens;
    
    private int compilingPhase;
    
    private final CszzSource source;
    
    private final CompileContext context;

    public CompilationUnit(@Nonnull CszzSource source,CompileContext context) {
        this.source = source;
        this.context = context;
        lexer = context.createLexer(this,source.getText());
        tokens = context.createTokenStream(this,lexer);
        parser = context.createParser(this,tokens);
        astBuilder = context.createAstBuilder(this,parser);
        //should move to configuration?
        astBuilder.importPackage("java.lang");
        astBuilder.importPackage("java.util");
        astBuilder.importPackage("java.net");
        astBuilder.importPackage("java.io");
        astBuilder.importPackage("java.nio");
        astBuilder.importPackage("cszz.io");
        semanticAnalyzer = context.createSemanticAnalyzer(this,context.getAstLoader());
        compile(PHASE_INITIALIZE);
    }
    
    protected void doCompilePhase(int phase){
        if(phase==PHASE_INITIALIZE){
            parseInit(context.getDiagnosisHandler());
        }else if(phase==PHASE_PARSING){
            parseMeta(context.getDiagnosisHandler());
        }else if(phase == PHASE_BUILDAST){
            parseBody(context.getDiagnosisHandler());
        }else if(phase==PHASE_SEMANTIC){
            //TODO remove PHASE_SEMANTIC
            //semanticAnalysis(context.getDiagnosisHandler());
        }else if(phase == PHASE_CLASSGEN){
            CodeGenerator codeGenerator = context.createCodeGenerator(this);
            if(codeGenerator==null){
                throw new IllegalStateException("CodeGenerator is missing");
            }
            codeGenerator.generate(this.getAst());
        }
    }
    
    public void compile(int targetPhase){
        while(compilingPhase<targetPhase){
            compilingPhase++;
            doCompilePhase(compilingPhase);
        }
    }
    
    protected void parseInit(DiagnosisHandler semanticErrorHandler){
        parse(semanticErrorHandler,AstBuilder.PARSING_PHASE_INIT);
    }
    
    protected void parseMeta(DiagnosisHandler semanticErrorHandler){
        parse(semanticErrorHandler, AstBuilder.PARSING_PHASE_META);
    }
    
    public void parseBody(DiagnosisHandler semanticErrorHandler){
        parse(semanticErrorHandler, AstBuilder.PARSING_PHASE_ALL);
    }

    protected void parse(DiagnosisHandler semanticErrorHandler, int targetParsingPhase) {
        astBuilder.setDiagnosisHandler(semanticErrorHandler);
        astBuilder.compile(targetParsingPhase,context.getAstLoader());
    }

    @Nonnull
    public ClassNode getAst() {
        return astBuilder.getAst();
    }

    @Nonnull
    public AstBuilder getAstBuilder() {
        return astBuilder;
    }

    @Nonnull
    public SemanticAnalyzer getSemanticAnalyzer() {
        return semanticAnalyzer;
    }

    @Nonnull
    public CommonTokenStream getTokenStream() {
        return tokens;
    }

    public CszzLexer getLexer() {
        return lexer;
    }

    public CszzParser getParser() {
        return parser;
    }

    public CommonTokenStream getTokens() {
        return tokens;
    }

    public CszzSource getSource() {
        return source;
    }

    public CompileContext getCompileContext() {
        return context;
    }

}
