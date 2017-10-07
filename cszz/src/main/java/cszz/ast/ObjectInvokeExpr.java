
package cszz.ast;
import java.io.*;
import java.lang.reflect.Modifier;
import java.nio.*;
import java.net.*;
import java.util.*;
import javax.annotation.Nullable;
import cszz.AmbiguousMethodException;
import cszz.MethodNotFoundException;
import cszz.core.ObjectType;
import cszz.core.ExecutableDescriptor;
import cszz.core.MethodDescriptor;
import cszz.core.Type;
import cszz.core.Types;
import cszz.util.Parameters;
import cszz.util.AstUtil;
/**
 *
 *  
 */
public class ObjectInvokeExpr extends InvocationExpr{
    
    public static ObjectInvokeExpr create(ExprNode target , String methodName,ExprNode[] args) throws MethodNotFoundException, AmbiguousMethodException {
        return create(target, methodName, args, null);
    }
    
    public static ObjectInvokeExpr create(ExprNode target , String methodName,ExprNode[] args,@Nullable ClassNode caller) throws MethodNotFoundException, AmbiguousMethodException {
        ObjectType targetType = (ObjectType) target.getType();
        ClassNode clazz = targetType.getClassNode();
        boolean recursive = ! "<init>".equals(methodName);
        //MethodNode[] candidates = AstUtil.listAccessibleMethods(clazz, caller , recursive);
        List<ExecutableDescriptor> candidates = new LinkedList();
        candidates.addAll(Arrays.asList(targetType.getMethodDescriptors(caller,recursive,true)));
        candidates.addAll(Arrays.asList(targetType.getConstructorDescriptors(caller)));
        MethodSelection ms = applyMethod(targetType, methodName, args,candidates.toArray(new ExecutableDescriptor[candidates.size()]));
        ExecutableDescriptor md = ms.selectedMethod;
        if(AstUtil.isStatic(md.getModifier())){
            throw new MethodNotFoundException(methodName + " is static");
        }
        return new ObjectInvokeExpr(target,ms.selectedMethod ,ms.appliedArguments);
    }

    private ExprNode invokeTarget;
    
    //private final ClassNode specialClass;

    public ObjectInvokeExpr(ExprNode invokeTarget, ExecutableDescriptor method, ExprNode[] args) {
        super((ObjectType)invokeTarget.getType(),method, args);
        Parameters.requireTrue(!Modifier.isStatic(method.getModifier()));
        this.invokeTarget = invokeTarget;
    }

    public ExprNode getInvokeTarget() {
        return invokeTarget;
    }

//    public ClassNode getSpecialClass() {
//        return specialClass;
//    }

    @Override
    public List<AstNode> getChildren() {
        List<AstNode> list = new LinkedList();
        addChild(list, invokeTarget);
        addChild(list, arguments);
        return list;
    }
    
    
    
}
