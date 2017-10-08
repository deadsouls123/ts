package cszz.ast;

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cszz.FieldNotFoundException;
import cszz.core.FieldDescriptor;
import cszz.core.ObjectType;
import cszz.core.Type;
import cszz.util.AstUtil;
import cszz.util.Parameters;
/**
 *
 *  
 */
public class ObjectFieldExpr extends FieldExpr{
    
    protected ExprNode target;

    public ObjectFieldExpr(ExprNode target, FieldDescriptor field) {
        super(field);
        Parameters.requireFalse(Modifier.isStatic(field.getModifier()));
        this.target = target;
    }
    
    /**
     * @return the target,null if the field is static
     */
    @Nullable
    public ExprNode getTarget() {
        return target;
    }
    
    @Nonnull
    public static FieldExpr create(@Nonnull ExprNode target,String fieldName,@Nullable ClassNode caller) throws FieldNotFoundException{
        Type type = target.getType();
        if(!(type instanceof ObjectType)){
            throw new UnsupportedOperationException("unsupported type:" + type);
        }
        ObjectType classType = (ObjectType) type;
        FieldDescriptor field = getField(classType, fieldName,caller);
        if(AstUtil.isStatic(field.getModifier())){
            throw new FieldNotFoundException(fieldName + " is static");
        }
        return new ObjectFieldExpr(target,field);
    }

    @Override
    public List<AstNode> getChildren() {
        return Collections.singletonList(target);
    }
    
    

}
