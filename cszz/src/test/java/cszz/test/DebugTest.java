package cszz.test;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import cszz.compiler.Diagnosis;
import cszz.compiler.DiagnosisHandler;
import cszz.compiler.codegen.Ast2Class;
import cszz.tool.MemoryOutputManager;
import junit.framework.Assert;

/**
 *
 * 
 */
public class DebugTest extends JointCompilerTestCase {
    
    boolean hasError = false;
    
    public DebugTest() {
        super();
        final DiagnosisHandler oldHandler = super.diagnosisHandler;
        this.setDiagnosisHandler(new DiagnosisHandler() {
            @Override
            public void handleDiagnosis(Diagnosis diagnosis) {
                oldHandler.handleDiagnosis(diagnosis);
                if(diagnosis.getKind().isError()){
                    DebugTest.this.hasError = true;
                }
            }
        });
    }
    
    @Test
    public void test() throws IOException{
        File debugDir = new File("debug");
        if(debugDir.exists()){
            this.addCszzAndJavaSourceDir(debugDir);
            this.setCodeGenerator(new Ast2Class(new MemoryOutputManager()));
            compile();
        }
        if(this.hasError) Assert.fail("compile error");
    }
    
}
