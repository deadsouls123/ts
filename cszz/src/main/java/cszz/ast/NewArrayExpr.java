package cszz.ast;

import java.util.Objects;

import cszz.core.Type;
import cszz.core.Types;

public class NewArrayExpr extends ExprNode{
    
    protected Type componentType;
    
    protected ExprNode size;
    
    public NewArrayExpr(Type componentType,ExprNode size){
            this.componentType = componentType;
            this.size = size;
    }

    @Override
    public Type getType() {
        return Types.getArrayType(getComponentType());
    }

    /**
     * @return the componentType
     */
    public Type getComponentType() {
        return componentType;
    }

    /**
     * @param componentType the componentType to set
     */
    public void setComponentType(Type componentType) {
        Objects.requireNonNull(componentType);
        this.componentType = componentType;
    }

    /**
     * @return the size
     */
    public ExprNode getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(ExprNode size) {
        Objects.requireNonNull(size);
        this.size = size;
    }
    
}
