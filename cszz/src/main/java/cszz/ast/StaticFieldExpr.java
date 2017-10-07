
package cszz.ast;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import javax.annotation.Nullable;
import cszz.FieldNotFoundException;
import cszz.core.FieldDescriptor;
import cszz.core.Types;
import cszz.util.AstUtil;
/**
 *
 *  
 */
public class StaticFieldExpr extends FieldExpr{
    
    private ClassReference clazz;
    
    public static StaticFieldExpr create(ClassReference clazz,String fieldName,@Nullable ClassNode caller) throws FieldNotFoundException{
        FieldDescriptor  field = getField(Types.getClassType(clazz.getReferencedClassNode()),fieldName,caller);
        if(!AstUtil.isStatic(field.getModifier())){
            throw new FieldNotFoundException(fieldName + " is not static");
        }
        return new StaticFieldExpr(clazz, field);
    }

    public StaticFieldExpr(ClassReference clazz, FieldDescriptor field) {
        super(field);
        if(!AstUtil.isStatic(field.getModifier())){
            throw new IllegalArgumentException("static field required");
        }
        this.clazz = clazz;
    }

    public ClassReference getClassReference() {
        return clazz;
    }

}
