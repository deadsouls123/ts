
package cszz.ast;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import cszz.core.FieldDescriptor;
import cszz.core.Type;
/**
 *
 * 
 */
public class FieldNode extends VarObject implements FieldDescriptor{
    
    private final ClassNode classNode;

    protected FieldNode(ClassNode classNode,Type type,String name,int modifier) {
        super(modifier,type,name);
        this.classNode = classNode;
    }

    @Override
    public int getModifier() {
        return modifier;
    }

    @Override
    public FieldNode getFieldNode() {
        return this;
    }

    public ClassNode getClassNode() {
        return classNode;
    }

}
