package cszz.ast;

import java.util.LinkedList;
import java.util.List;

import cszz.core.Type;

public class MultiStmtExpr extends ExprNode{
    
    public final List<Statement> stmts = new LinkedList<>();
    
    public ExprNode reference;
    
    public MultiStmtExpr(List<Statement> stmts,ExprNode reference){
        this.stmts.addAll(stmts);
        this.reference = reference;
    }
    
    @Override
    public List<AstNode> getChildren(){
        List<AstNode> ls = new LinkedList();
        addChild(ls,stmts);
        return ls;
    }

    @Override
    public Type getType() {
        return getType(reference);
    }
}
