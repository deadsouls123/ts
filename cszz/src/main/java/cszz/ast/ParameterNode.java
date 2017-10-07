
package cszz.ast;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import cszz.core.Type;
/**
 *
 *  
 */
public class ParameterNode extends VarObject{
    
    private final MethodNode method;

    protected ParameterNode(MethodNode method,Type type,String name) {
        //TODO add modifier parameter
        super(0,type,name);
        this.method = method;
    }

    public MethodNode getMethod() {
        return method;
    }

}
