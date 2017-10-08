package cszz.ast;

import cszz.core.Type;
/**
 *
 *  
 */
public class MathExpr extends BinaryExpr{

    public MathExpr(ExprNode expr1, ExprNode expr2, String operation) {
        super(expr1, expr2, operation);
    }
    
    @Override
    public Type getType() {
        return getType(getExpr1());
    }
    

}
