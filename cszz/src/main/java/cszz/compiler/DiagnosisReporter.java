package cszz.compiler;

import cszz.util.OffsetRangeHelper;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

/**
 *
 * 
 */
public class DiagnosisReporter {

    private final CompileContext compileContext;

    private final DiagnosisHandler disgnosisHandler;

    private final CszzSource cszzSource;

    public DiagnosisReporter(CompileContext compileContext, DiagnosisHandler disgnosisHandler, CszzSource cszzSource) {
        this.compileContext = compileContext;
        this.disgnosisHandler = disgnosisHandler;
        this.cszzSource = cszzSource;
    }
    
    public void report(Diagnosis.Kind kind,String message,OffsetRange offset){
        Diagnosis diagnosis = new Diagnosis(compileContext, kind, offset, message, cszzSource);
        this.disgnosisHandler.handleDiagnosis(diagnosis);
    }
    
    public void report(Diagnosis.Kind kind,String message,Token start,Token end){
        OffsetRange offset = OffsetRangeHelper.getOffsetRange(start, end);
        this.report(kind, message, offset);
    }
    
    public void report(Diagnosis.Kind kind,String message,Token offset){
        this.report(kind, message, offset,offset);
    }
    
    public void report(Diagnosis.Kind kind,String message,ParserRuleContext parserRuleCtx){
        OffsetRange offset = OffsetRangeHelper.getOffsetRange(parserRuleCtx);
        this.report(kind, message, offset);
    }
    
    public void report(Diagnosis.Kind kind,String message){
        this.report(kind, message,OffsetRange.NONE);
    }
    
    public CompileContext getCompileContext() {
        return compileContext;
    }

    public DiagnosisHandler getDisgnosisHandler() {
        return disgnosisHandler;
    }

    public CszzSource getCszzSource() {
        return cszzSource;
    }
    
}
