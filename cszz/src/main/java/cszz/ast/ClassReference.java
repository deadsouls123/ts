package cszz.ast;

/**
 *
 *  
 */
public class ClassReference extends AstNode{
    
    private ClassNode referencedClassNode;

    public ClassReference(ClassNode referencedClassNode) {
        this.referencedClassNode = referencedClassNode;
    }

    public ClassNode getReferencedClassNode() {
        return referencedClassNode;
    }
    
}
