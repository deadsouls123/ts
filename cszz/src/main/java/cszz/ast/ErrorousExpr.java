package cszz.ast;

import java.util.Arrays;
import java.util.List;

import cszz.core.Type;
import cszz.core.Types;

/**
 *
 *  
 */

public class ErrorousExpr extends ExprNode{
    
    private AstNode[] children;

    public ErrorousExpr(AstNode... children) {
        this.children = children;
    }

    @Override
    public List<AstNode> getChildren() {
        return Arrays.asList(children);
    }

    @Override
    public Type getType() {
        return Types.getRootType();
    }

}
