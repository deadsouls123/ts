package cszz.tool;

import cszz.ast.ClassNode;
import cszz.compiler.CodeGenerator;
import cszz.compiler.codegen.Ast2Class;

/**
 *
 * 
 */
public class ClassWriter implements CodeGenerator{

    private OutputManager outputManager;

    public ClassWriter(OutputManager outputManager) {
        this.outputManager = outputManager;
    }

    @Override
    public void generate(ClassNode classNode) {
        if (outputManager != null) {
            Ast2Class ast2class = new Ast2Class(outputManager);
            ast2class.generate(classNode);
        }
    }
    
}
