
package cszz.ast;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import javax.annotation.Nullable;
import cszz.core.Type;
import cszz.core.Types;
/**
 *
 *  
 */
public class UnknownInvocationExpr extends ExprNode{
    
    private AstNode target;
    
    private String methodName;
    
    private ExprNode[] arguments;

    public UnknownInvocationExpr(@Nullable AstNode target, String methodName, ExprNode[] arguments) {
        this.target = target;
        this.methodName = methodName;
        this.arguments = arguments;
    }

    @Nullable
    public AstNode getTarget() {
        return target;
    }

    public String getMethodName() {
        return methodName;
    }

    public ExprNode[] getArguments() {
        return arguments;
    }

    @Override
    public Type getType() {
        return Types.getRootType();
    }

    @Override
    public List<AstNode> getChildren() {
        LinkedList<AstNode> ls = new LinkedList();
        ls.add(target);
        ls.addAll(Arrays.asList(arguments));
        return ls;
    }
    
    

}
