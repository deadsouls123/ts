package cszz.compiler;

import java.io.PrintStream;

/**
 *
 * 
 */
public class StandardDiagnosisHandler implements DiagnosisHandler{
    
    public static StandardDiagnosisHandler INSTANCE = new StandardDiagnosisHandler();

    private final PrintStream errOut;
    
    private final PrintStream stdOut;

    public StandardDiagnosisHandler() {
        this(System.out,System.err);
    }

    public StandardDiagnosisHandler(PrintStream stdOut,PrintStream errOut) {
        this.stdOut = stdOut;
        this.errOut = errOut;
    }

    @Override
    public void handleDiagnosis(Diagnosis diagnosis) {
        boolean isError = diagnosis.getKind().isError();
        if(isError){
            CompileContext ctx = diagnosis.getContext();
            ctx.stopCompile(ctx.getCompilingPhase());
        }
        PrintStream o = isError ? stdOut : errOut;
        o.println(diagnosis);
    }

}
