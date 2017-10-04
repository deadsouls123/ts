package cszz.ast;
import java.util.*;
import cszz.core.*;
public class ThisExpr extends ExprNode{
    
    private final ObjectType classType;
    
    public ThisExpr(ObjectType classType){
            this.classType = classType;
    }
    
    public ThisExpr(ClassNode classNode){
        this(Types.getClassType(classNode));
    }

    @Override
    public Type getType() {
        return classType;
    }
    
}
