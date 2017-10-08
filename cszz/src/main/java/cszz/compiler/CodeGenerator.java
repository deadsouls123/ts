package cszz.compiler;

import cszz.ast.ClassNode;
/**
 *
 *  
 */
public interface CodeGenerator {
    
    void generate(ClassNode classNode);
    
}
