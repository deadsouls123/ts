package cszz.test;

import java.util.Objects;
import junit.framework.Assert;
import cszz.compiler.Diagnosis;
import cszz.compiler.DiagnosisHandler;
import cszz.tool.JointFileSystemCompiler;

/**
 *
 * 
 */
public class JointCompilerTestCase extends JointFileSystemCompiler {

    public JointCompilerTestCase() {
        super();
        final DiagnosisHandler oldDiagnosisHandler = super.diagnosisHandler;
        this.diagnosisHandler = new DiagnosisHandler() {
            @Override
            public void handleDiagnosis(Diagnosis diagnosis) {
                oldDiagnosisHandler.handleDiagnosis(diagnosis);
                if (diagnosis.getKind().isError()) {
                    Assert.fail(Objects.toString(diagnosis));
                }
            }
        };
    }

}
