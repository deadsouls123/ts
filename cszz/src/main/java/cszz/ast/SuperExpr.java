
package cszz.ast;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import cszz.core.Type;
import cszz.core.Types;
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
