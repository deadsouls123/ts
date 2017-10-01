package kalang.ast;
import java.util.*;
import kalang.core.*;
public class ThrowStmt extends Statement{
    
    public ExprNode expr;
    
    public ThrowStmt(ExprNode expr){
            this.expr = expr;
    }
    
    public List<AstNode> getChildren(){
        List<AstNode> ls = new LinkedList();
        addChild(ls,expr);
        return ls;
    }

}