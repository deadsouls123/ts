package cszz.ast;

import cszz.core.Type;

/**
 *
 *  
 */
public class SuperExpr extends ExprNode{
    
    private ClassNode classNode;

    public SuperExpr(ClassNode classNode) {
        this.classNode = classNode;
    }

    @Override
    public Type getType() {
        if(classNode.superType==null){
            return null;
        }
        return classNode.superType;
    }

    public ClassNode getClassNode() {
        return classNode;
    }

}
