package cszz.ast;
import java.util.*;
import cszz.core.*;
public class MultiStmt extends Statement{
    
    public final List<Statement> statements = new LinkedList<>();
    
    public MultiStmt(){
        
    }
    
    @Override
    public List<AstNode> getChildren(){
        List<AstNode> ls = new LinkedList();
        addChild(ls,statements);
        return ls;
    }
    
}
