package cszz.util;

import javax.annotation.Nullable;
import cszz.ast.ClassNode;
import cszz.core.ObjectType;
import cszz.core.MethodDescriptor;

/**
 *
 * 
 */
public class ClassTypeUtil {
    
    @Nullable
    public static MethodDescriptor getMethodDescriptor(ObjectType clazzType,String declarationKey,ClassNode caller,boolean includeSuperType,boolean includeInterface){
        MethodDescriptor[] mds = clazzType.getMethodDescriptors(caller, includeSuperType,includeInterface);
        for(MethodDescriptor m:mds){
            if(m.getDeclarationKey().equals(declarationKey)) return m;
        }
        return null;
    }
    
    @Nullable
    public static MethodDescriptor getMethodDescriptor(ObjectType[] clazzType,String declarationKey,ClassNode caller,boolean includeSuperType,boolean includeInterface){
        for(ObjectType c:clazzType){
            MethodDescriptor m = getMethodDescriptor(c, declarationKey, caller, includeSuperType,includeInterface);
            if(m!=null) return m;
        }
        return null;
    }
    

}
