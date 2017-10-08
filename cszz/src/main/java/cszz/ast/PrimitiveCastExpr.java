package cszz.ast;

import java.util.LinkedList;
import java.util.List;

import cszz.core.PrimitiveType;
import cszz.core.Type;

public class PrimitiveCastExpr extends ExprNode{
    
    protected PrimitiveType fromType;
    
    protected PrimitiveType toType;
    
    protected ExprNode expr;
    
    public PrimitiveCastExpr(PrimitiveType fromType,PrimitiveType toType,ExprNode expr){
            this.fromType = fromType;
            this.toType = toType;
            this.expr = expr;
    }
        
    @Override
    public List<AstNode> getChildren(){
        List<AstNode> ls = new LinkedList();
        addChild(ls,expr);
        return ls;
    }

    @Override
    public Type getType() {
        return getToType();
    }

    /**
     * @return the fromType
     */
    public PrimitiveType getFromType() {
        return fromType;
    }

    /**
     * @return the toType
     */
    public PrimitiveType getToType() {
        return toType;
    }

    /**
     * @return the expr
     */
    public ExprNode getExpr() {
        return expr;
    }

}
