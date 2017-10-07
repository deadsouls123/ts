package cszz.core;

import cszz.ast.FieldNode;

/**
 *
 * 
 */
public interface FieldDescriptor {

    public String getName();

    public Type getType();

    public int getModifier() ;

    public FieldNode getFieldNode() ;

}
