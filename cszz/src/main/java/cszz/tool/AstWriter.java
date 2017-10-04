package cszz.tool;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import cszz.ast.ClassNode;
import cszz.compiler.CodeGenerator;
import cszz.util.AstOutputUtil;

/**
 *
 * @author Kason Yang
 */
public class AstWriter implements CodeGenerator{

    OutputManager outputManager;

    public AstWriter(OutputManager outputManager) {
        this.outputManager = outputManager;
    }
    
    @Override
    public void generate(ClassNode classNode) {
        String str = AstOutputUtil.toString(classNode);
        try {
            try (OutputStream os = outputManager.createOutputStream(classNode.name)) {
                os.write(str.getBytes());
            }
        } catch (IOException ex) {
            Logger.getLogger(AstWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
