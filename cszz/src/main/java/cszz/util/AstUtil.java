package cszz.util;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cszz.AmbiguousMethodException;
import cszz.MethodNotFoundException;
import cszz.ast.AssignExpr;
import cszz.ast.BlockStmt;
import cszz.ast.ClassNode;
import cszz.ast.ClassReference;
import cszz.ast.ExprNode;
import cszz.ast.ExprStmt;
import cszz.ast.FieldExpr;
import cszz.ast.FieldNode;
import cszz.ast.InvocationExpr;
import cszz.ast.MethodNode;
import cszz.ast.ObjectFieldExpr;
import cszz.ast.ObjectInvokeExpr;
import cszz.ast.ParameterExpr;
import cszz.ast.ParameterNode;
import cszz.ast.ReturnStmt;
import cszz.ast.Statement;
import cszz.ast.StaticFieldExpr;
import cszz.ast.SuperExpr;
import cszz.ast.ThisExpr;
import cszz.core.ClassType;
import cszz.core.ConstructorDescriptor;
import cszz.core.ExecutableDescriptor;
import cszz.core.FieldDescriptor;
import cszz.core.MethodDescriptor;
import cszz.core.ObjectType;
import cszz.core.ParameterDescriptor;
import cszz.core.Type;
import cszz.core.Types;

public class AstUtil {

    @Nonnull
    public static List<MethodDescriptor> getUnimplementedMethod(ClassNode theClass){
        List<MethodDescriptor> list = new LinkedList();
        for(ObjectType i:theClass.getInterfaces()){
            list.addAll(getUnimplementedMethod(theClass,i));
        }
        return list;
    }
    
    @Nonnull
    public static List<MethodDescriptor> getUnimplementedMethod(ClassNode theClass, ObjectType theInterface) {
        ClassType theType = Types.getClassType(theClass);
        MethodDescriptor[] implementedMethods = theType.getMethodDescriptors(theClass, true, false);
        List<MethodDescriptor> list = new LinkedList();
        for (MethodDescriptor m : theInterface.getMethodDescriptors(theClass, false,true)) {
            if(ModifierUtil.isDefault(m.getModifier())) continue;
            String name = m.getName();
            Type[] types = m.getParameterTypes();
            MethodDescriptor overridingMd = MethodUtil.getMethodDescriptor(implementedMethods,name,types);
            if (overridingMd == null 
                    //TODO move check to where method declare
                    || !OverrideUtil.overridingCompatible(overridingMd.getModifier(), m.getModifier()) 
                    || !OverrideUtil.returnTypeCompatible(overridingMd.getReturnType(), m.getReturnType())
                    || !OverrideUtil.exceptionTypeCompatible(overridingMd.getExceptionTypes(), m.getExceptionTypes())
                    ) {
                list.add(m);
            }
        }
        return list;
    }
    
    public static boolean createEmptyConstructor(ClassNode clazzNode){
        ObjectType supType = clazzNode.superType;
        if(supType==null){
            throw new RuntimeException("super type is null:" + clazzNode.name);
        }
       ConstructorDescriptor[] constructors = supType.getConstructorDescriptors(clazzNode);
        ConstructorDescriptor m = MethodUtil.getConstructorDescriptor(constructors, null);
       if(m!=null){
            MethodNode mm = clazzNode.createMethodNode(Types.VOID_TYPE,m.getName(),m.getModifier());
            for(Type e : m.getExceptionTypes()) mm.addExceptionType(e);
            ParameterDescriptor[] pds = m.getParameterDescriptors();
            for(ParameterDescriptor pd:pds){
                ParameterNode p = mm.createParameter(pd.getType(), pd.getName());
                //TODO update mm.createParameter
                p.modifier = pd.getModifier();
            }
            BlockStmt body = mm.getBody();
            ParameterNode[] parameters = mm.getParameters();
            ExprNode[] params = new ExprNode[parameters.length];
            for(int i=0;i<params.length;i++){
                params[i] = new ParameterExpr(parameters[i]);
            }
            body.statements.add(
                    new ExprStmt(
                            new ObjectInvokeExpr(
                                    new SuperExpr(clazzNode), 
                                    m,
                                    params)
                    )
            );
            return true;
       }else{
           return false;
       }
    }
    
    public static boolean containsConstructor(ClassNode clazz){
        MethodNode[] dms = clazz.getDeclaredMethodNodes();
        for(MethodNode m:dms){
            if("<init>".equals(m.getName())) return true;
        }
        return false;
    }

    public static ExecutableDescriptor[] filterMethodByName(ExecutableDescriptor[] mds, String methodName) {
        List<ExecutableDescriptor> methods = new ArrayList(mds.length);
        for (ExecutableDescriptor m : mds) {
            if (m.getName().equals(methodName)) {
                methods.add(m);
            }
        }
        return methods.toArray(new ExecutableDescriptor[methods.size()]);
    }

    public static ExprNode matchType(Type from, Type target, ExprNode expr) {
        if (from.equals(target)) {
            return expr;
        }
        return BoxUtil.assign(expr, from, target);
    }

    /**
     * 
     * @param args
     * @param from
     * @param target
     * @return array when matched,null when not
     */
    @Nullable
    public static ExprNode[] matchTypes(ExprNode[] args,Type[] from, Type[] target) {
        if(args==null) return null;
        if(from==null || from.length==0){
            if(target==null || target.length==0){
                return args;
            }else{
                return null;
            }
        }
        if (from.length != target.length || from.length!=args.length) {
            return null;
        }
        if(from.length==0) return new ExprNode[0];
       ExprNode[] newParams = new ExprNode[from.length];
        for (int i = 0; i < from.length; i++) {
            Type f = from[i];
            Type t = target[i];
            newParams[i] = matchType(f, t, args[i]);
            if(newParams[i]==null) return null;
        }
        return newParams;
    }
    
    @Nullable
    public static ExecutableDescriptor getExactedMethod(ObjectType targetType,ExecutableDescriptor[] candidates,String methodName,@Nullable Type[] types){
        ExecutableDescriptor[] methods = filterMethodByName(candidates, methodName);
        for(ExecutableDescriptor m:methods){
           Type[] mdTypes = m.getParameterTypes();
           if(Arrays.equals(mdTypes, types)) return m;
           if(mdTypes==null || mdTypes.length==0){
               if(types==null || types.length==0) return m;
           }
        }
        return null;
    }

    @Nullable
    public static Type[] getExprTypes(ExprNode[] exprs) {
        if(exprs==null) return null;
        Type[] types = new Type[exprs.length];
        for(int i=0;i<types.length;i++){
            types[i] = exprs[i].getType();
        }
        return types;
    }
    
    public static boolean isStatic(int modifier){
            return Modifier.isStatic(modifier);
    }
    
    public static boolean isConstructorCallStatement(Statement stmt){
        try{
            ExprStmt exprStmt = (ExprStmt) stmt;
            InvocationExpr invExpr = (InvocationExpr) exprStmt.getExpr();
            ExecutableDescriptor method = invExpr.getMethod();
            return method.getName().equals("<init>") && !Modifier.isStatic(method.getModifier());
        }catch(ClassCastException ex){
            return false;
        }
    }

    public static boolean isConstructor(MethodNode m) {
        return !isStatic(m.getModifier()) && m.getName().equals("<init>");
    }

    public static boolean hasConstructorCallStatement(List<Statement> statements) {
        for(Statement s:statements){
            if(isConstructorCallStatement(s)) return true;
        }
        return false;
    }
    
    public static Statement createDefaultSuperConstructorCall(ClassNode clazz) throws MethodNotFoundException, AmbiguousMethodException{
       SuperExpr thisExpr = new SuperExpr(clazz);
        return new ExprStmt(
                ObjectInvokeExpr.create(thisExpr, "<init>", null,clazz)
        );
    }
    
    
    public static boolean hasSetter(ClassNode clazz,FieldNode field){
        ClassType type = Types.getClassType(clazz);
        MethodDescriptor md = MethodUtil.getMethodDescriptor(type.getMethodDescriptors(clazz, true, true), "set" + NameUtil.firstCharToUpperCase(field.getName()),new Type[]{ field.getType()});
        return md !=null;
    }
    
    public static boolean hasGetter(ClassNode clazz,FieldNode field){
        ClassType type = Types.getClassType(clazz);
        MethodDescriptor md = MethodUtil.getMethodDescriptor(type.getMethodDescriptors(clazz, true, true), "get" + NameUtil.firstCharToUpperCase(field.getName()) , null);
        return md != null;
    }
    
    public static void createGetter(ClassNode clazz,FieldDescriptor field,int accessModifier){
        String fn = field.getName();
        String getterName = "get" + NameUtil.firstCharToUpperCase(fn);
        boolean isStatic = isStatic(field.getModifier());
        if(isStatic){
            accessModifier |= Modifier.STATIC;
        }
        MethodNode getter = clazz.createMethodNode(field.getType(),getterName,accessModifier);
        //getter.offset = field.offset;
        BlockStmt body = getter.getBody();
        FieldExpr fe;
        ClassReference cr = new ClassReference(clazz);
        if(isStatic){
            fe = new StaticFieldExpr(cr, field);
        }else{
            fe = new ObjectFieldExpr(new ThisExpr(Types.getClassType(clazz)), field);
        }
        body.statements.add(new ReturnStmt(fe));
    }
    
    public static void createSetter(ClassNode clazz,FieldDescriptor field,int accessModifier){
        String fn = field.getName();
        String setterName = "set" + NameUtil.firstCharToUpperCase(fn);
        boolean isStatic = isStatic(field.getModifier());
        if(isStatic){
            accessModifier |= Modifier.STATIC;
        }
        MethodNode setter = clazz.createMethodNode(Types.VOID_TYPE,setterName,accessModifier);
        //setter.offset = field.offset;
        ParameterNode param = setter.createParameter(field.getType(), field.getName());
        BlockStmt body = setter.getBody();
        FieldExpr fe;
        ExprNode paramVal = new ParameterExpr(param);
        ClassReference cr = new ClassReference(clazz);
        if(isStatic){
            fe = new StaticFieldExpr(cr, field);
        }else{
            fe = new ObjectFieldExpr(new ThisExpr(Types.getClassType(clazz)), field);
        }
        body.statements.add(new ExprStmt(new AssignExpr(fe,paramVal)));
    }

    public static ClassNode createClassNodeWithInterfaces(String name,@Nullable ObjectType superType,@Nullable ObjectType... interfaces) {
        ClassNode cn = new ClassNode();
        cn.name = name;
        cn.superType = superType==null ? Types.getRootType() : superType;
        if(interfaces!=null){
            for(ObjectType itf:interfaces) cn.addInterface(itf);
        }
        return cn;
    }
    
    public static ClassNode createArrayAst(String component) {
        ClassNode clazz = new ClassNode();
        clazz.name = component + "[]";
        clazz.superType = Types.getRootType();
        return clazz;
    }
    
    public static ClassNode[] listInnerClasses(ClassNode classNode,boolean recursive){
        List<ClassNode> classes = new LinkedList();
        for(ClassNode ic:classNode.classes){
            classes.add(ic);
            if(recursive){
                classes.addAll(Arrays.asList(listInnerClasses(ic,true)));
            }
        }
        return classes.toArray(new ClassNode[classes.size()]);
    }
    
    public static String[] listInnerClassesNames(ClassNode clazz,boolean recursive){
        ClassNode[] classes = listInnerClasses(clazz, recursive);
        String[] names = new String[classes.length];
        for(int i=0;i<classes.length;i++){
            names[i] = classes[i].name;
        }
        return names;
    }
    
}
