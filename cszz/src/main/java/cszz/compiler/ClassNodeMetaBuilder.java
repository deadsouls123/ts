package cszz.compiler;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import cszz.antlr.CszzParser;
import cszz.antlr.CszzParser.MethodDeclContext;
import cszz.antlr.CszzParserBaseVisitor;
import cszz.ast.AnnotationNode;
import cszz.ast.AssignExpr;
import cszz.ast.AssignableExpr;
import cszz.ast.BlockStmt;
import cszz.ast.ClassNode;
import cszz.ast.ClassReference;
import cszz.ast.ExprNode;
import cszz.ast.ExprStmt;
import cszz.ast.FieldNode;
import cszz.ast.MethodNode;
import cszz.ast.ObjectFieldExpr;
import cszz.ast.ParameterExpr;
import cszz.ast.ParameterNode;
import cszz.ast.StaticFieldExpr;
import cszz.ast.ThisExpr;
import cszz.core.GenericType;
import cszz.core.MethodDescriptor;
import cszz.core.ModifierConstant;
import cszz.core.NullableKind;
import cszz.core.ObjectType;
import cszz.core.Type;
import cszz.core.Types;
import cszz.exception.Exceptions;
import cszz.util.AstUtil;
import cszz.util.ClassTypeUtil;
import cszz.util.MethodUtil;
import cszz.util.ModifierUtil;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

/**
 *
 * 
 */
public class ClassNodeMetaBuilder extends CszzParserBaseVisitor<Object> {

    AstBuilder astBuilder;
    private final ClassNodeBuilder classNodeBuilder;
    private ClassNode thisClazz;
    
    private Map<MethodNode,CszzParser.StatContext[]> methodStatsContexts = new HashMap();
    
    private Map<MethodNode,MethodDeclContext> methodContexts = new HashMap();
    
    private MethodNode method;
    private boolean inScriptMode;
    private final CompilationUnit compilationUnit;
    private DiagnosisReporter diagnosisReporter;
    

    public ClassNodeMetaBuilder(CompilationUnit compilationUnit, AstBuilder astBuilder, ClassNodeBuilder classNodeBuilder) {
        this.compilationUnit = compilationUnit;
        this.astBuilder = astBuilder;
        this.classNodeBuilder = classNodeBuilder;
    }

    public void build(ClassNode cn,boolean inScriptMode) {
        this.thisClazz = cn;
        astBuilder.thisClazz = cn;
        ParserRuleContext ctx = classNodeBuilder.getClassNodeDefContext(cn);
        if(ctx!=null) visit(ctx);
        for (ClassNode c : cn.classes) {
            build(c,false);
        }
    }

    @Override
    public Object visitClassDef(CszzParser.ClassDefContext ctx) {
        thisClazz.annotations.addAll(astBuilder.getAnnotations(ctx.annotation()));
        thisClazz.modifier = astBuilder.parseModifier(ctx.varModifier());
        List<Token> gnrTypes = ctx.genericTypes;
        if (gnrTypes != null && !gnrTypes.isEmpty()) {
            for (Token g : gnrTypes) {
                //TODO suport generic type bounds in syntax
                GenericType gt = new GenericType(g.getText(), Types.getRootType(), null, NullableKind.NONNULL);
                thisClazz.declareGenericType(gt);
            }
        }
        ObjectType superType = null;
        if (ctx.parentClass != null) {
            ObjectType parentClass = astBuilder.parseClassType(ctx.parentClass);
            if (parentClass != null) {
                superType = parentClass;
            }
        } else {
            superType = Types.getRootType();
        }
        if (Modifier.isInterface(thisClazz.modifier)) {
            //TODO update syntax to support:interface extends T1,T2...
            thisClazz.addInterface(superType);
        } else {
            thisClazz.superType = superType;
        }
        if (ctx.interfaces != null && ctx.interfaces.size() > 0) {
            for (CszzParser.ClassTypeContext itf : ctx.interfaces) {
                ObjectType itfClz = astBuilder.parseClassType(itf);
                if (itfClz != null) {
                    thisClazz.addInterface(itfClz);
                }
            }
        }
        if (this.isDeclaringNonStaticInnerClass()) {
            ClassNode parentClass = thisClazz.enclosingClass;
            if (parentClass == null) {
                throw Exceptions.unexceptedValue(parentClass);
            }
            thisClazz.createField(Types.getClassType(parentClass), "this$0", Modifier.PRIVATE | ModifierConstant.SYNTHETIC);
        }
        visit(ctx.classBody());
        if (!ModifierUtil.isInterface(thisClazz.modifier) 
                && !AstUtil.containsConstructor(thisClazz) 
                && !AstUtil.createEmptyConstructor(thisClazz)) {
            this.diagnosisReporter.report(Diagnosis.Kind.ERROR
                    , "failed to create constructor with no parameters", ctx
            );
        }
        MethodNode[] methods = thisClazz.getDeclaredMethodNodes();
        for (int i = 0; i < methods.length; i++) {
            MethodNode node = methods[i];
            BlockStmt body = node.getBody();
            if (body != null) {
                if (AstUtil.isConstructor(node)) {//constructor
                    if (this.isDeclaringNonStaticInnerClass()) {
                        ClassNode enclosingClass = thisClazz.enclosingClass;
                        if (enclosingClass == null) {
                            throw Exceptions.unexceptedValue(enclosingClass);
                        }
                        ParameterNode outerInstanceParam = node.createParameter(0, Types.getClassType(enclosingClass), "this$0");
                        ExprNode parentFieldExpr = astBuilder.getObjectFieldExpr(
                                new ThisExpr(Types.getClassType(thisClazz)), "this$0", ParserRuleContext.EMPTY
                        );
                        if (parentFieldExpr == null) {
                            throw Exceptions.unexceptedValue(parentFieldExpr);
                        }
                        body.statements.add(1, new ExprStmt(new AssignExpr((AssignableExpr) parentFieldExpr, new ParameterExpr(outerInstanceParam))));
                    }
                }
            }
        }
        for (FieldNode fieldNode : thisClazz.getFields()) {
            int mdf = fieldNode.modifier;
            if (!AstUtil.hasGetter(thisClazz, fieldNode)) {
                AstUtil.createGetter(thisClazz, fieldNode, mdf);
            }
            if (!AstUtil.hasSetter(thisClazz, fieldNode)) {
                AstUtil.createSetter(thisClazz, fieldNode, mdf);
            }
            fieldNode.modifier = ModifierUtil.setPrivate(mdf);
        }
        return null;
    }

    private boolean isNonStaticInnerClass(ClassNode clazz) {
        return clazz.enclosingClass != null && !Modifier.isStatic(clazz.modifier);
    }

    private boolean isDeclaringNonStaticInnerClass() {
        return isNonStaticInnerClass(thisClazz);
    }

    @Override
    public Object visitMethodDecl(CszzParser.MethodDeclContext ctx) {
        String name;
        Type type;
        boolean isOverriding = ctx.OVERRIDE() != null;
        if (ctx.prefix != null && ctx.prefix.getText().equals("constructor")) {
            type = Types.VOID_TYPE;
            name = "<init>";
        } else {
            if (ctx.type() == null) {
                type = Types.VOID_TYPE;
            } else {
                type = astBuilder.parseType(ctx.returnType);
            }
            name = ctx.name.getText();
        }
        List<CszzParser.TypeContext> paramTypesCtx = ctx.paramTypes;
        int modifier = astBuilder.parseModifier(ctx.varModifier());
        if(inScriptMode){
            modifier |= Modifier.STATIC;
        }
        Type[] paramTypes;
        String[] paramNames;
        if (paramTypesCtx != null) {
            int paramSize = paramTypesCtx.size();
            paramTypes = new Type[paramSize];
            paramNames = new String[paramSize];
            for(int i=0;i<paramSize;i++){
                CszzParser.TypeContext t = paramTypesCtx.get(i);
                paramTypes[i] = astBuilder.parseType(t);
                paramNames[i] = ctx.paramIds.get(i).getText();
            }
        }else{
            paramTypes = new Type[0];
            paramNames = new String[0];
        }
        //check method duplicated before generate java stub
        String mStr = MethodUtil.getDeclarationKey(name,paramTypes);
        boolean existed = Arrays.asList(thisClazz.getDeclaredMethodNodes()).stream().anyMatch((m)->{
            return MethodUtil.getDeclarationKey(m).equals(mStr);
        });
        if (existed) {
            //TODO should remove the duplicated method
            diagnosisReporter.report(Diagnosis.Kind.ERROR,"declare method duplicately:"+mStr, ctx);
            return null;
        }
        CszzParser.BlockStmtContext blockStmt = ctx.blockStmt();
        if(blockStmt==null){
            if(ModifierUtil.isInterface(thisClazz.modifier)){
                modifier |= Modifier.ABSTRACT;
            }else if(!Modifier.isAbstract(modifier)){
                diagnosisReporter.report(Diagnosis.Kind.ERROR, "method body required", ctx);
            }else if(!Modifier.isAbstract(thisClazz.modifier)){
                diagnosisReporter.report(Diagnosis.Kind.ERROR, "declare abstract method in non-abstract class", ctx);
            }
        }
        method = thisClazz.createMethodNode(type,name,modifier);
        for(int i=0;i<paramTypes.length;i++){
            method.createParameter(paramTypes[i], paramNames[i]);
        }
        for(AnnotationNode a:astBuilder.getAnnotations(ctx.annotation()))  method.addAnnotation(a);
        ObjectType superType = thisClazz.superType;
        if(superType==null){//the superType of interface may be null
            superType = Types.getRootType();
        }
        MethodDescriptor overriddenMd = ClassTypeUtil.getMethodDescriptor(superType, mStr, thisClazz, true,true);
        if(overriddenMd==null){
            overriddenMd = ClassTypeUtil.getMethodDescriptor(thisClazz.getInterfaces(), mStr, thisClazz, true,true);
        }
        if(isOverriding && overriddenMd==null){            
            diagnosisReporter.report(Diagnosis.Kind.ERROR,"method does not override any method", ctx);
        }
        if(!isOverriding && overriddenMd!=null){
            diagnosisReporter.report(Diagnosis.Kind.ERROR,"method override a method but not declare", ctx);
        }
        this.methodContexts.put(method, ctx);
        CszzParser.BlockStmtContext bstm = ctx.blockStmt();
        if(bstm!=null){
            List<CszzParser.StatContext> stats = bstm.stat();
            if(stats!=null) this.methodStatsContexts.put(method, stats.toArray(new CszzParser.StatContext[stats.size()]));
        }
        if (ctx.exceptionTypes != null) {
            for (Token et : ctx.exceptionTypes) {
                ObjectType exType = astBuilder.requireClassType(et);
                if(exType!=null){
                    method.addExceptionType(exType);
                }
            }
        }
        astBuilder.mapAst(method, ctx);
        MethodNode m = method;
        method=null;
        return m;
    }
    
    @Nullable
    public CszzParser.StatContext[] getStatContexts(MethodNode mn){
        return this.methodStatsContexts.get(mn);
    }

    @Override
    public Object visitClassBody(CszzParser.ClassBodyContext ctx) {
        for(CszzParser.FieldDeclContext f:ctx.fieldDecl()){
            visit(f);
        }
        for(CszzParser.MethodDeclContext m:ctx.methodDecl()){
            visit(m);
        }
        return null;
    }
    
    @Override
    public Void visitFieldDecl(CszzParser.FieldDeclContext ctx) {
        int mdf = astBuilder.parseModifier(ctx.varModifier());
        for(CszzParser.VarDeclContext vd:ctx.varDecl()){
            ExprNode initExpr;
            if(vd.expression()!=null){
                initExpr = astBuilder.visitExpression(vd.expression());
            }else{
                initExpr = null;
            }
            AstBuilder.VarInfo varInfo = astBuilder.varDecl(vd,initExpr==null
                    ?Types.getRootType()
                    :initExpr.getType()
            );
            FieldNode fieldNode = thisClazz.createField(varInfo.type, varInfo.name,mdf);
            //TODO simplify it
            if(initExpr!=null){
                if(AstUtil.isStatic(fieldNode.modifier)){
                    thisClazz.staticInitStmts.add(new ExprStmt(new AssignExpr(new StaticFieldExpr(new ClassReference(thisClazz), fieldNode), initExpr)));
                }else{
                    thisClazz.initStmts.add(new ExprStmt(
                            new AssignExpr(
                                    new ObjectFieldExpr(
                                            new ThisExpr(Types.getClassType(thisClazz)), fieldNode
                                    )
                                    , initExpr
                            )
                        )
                    );
                }
            }
        }
        return null;
    }

    @Override
    public Object visitScriptDef(CszzParser.ScriptDefContext ctx) {
        //FIXME fix filename
        //thisClazz.fileName = this.compilationUnit.getSource().getFileName();
        thisClazz.superType = Types.getRootType();
        List<MethodDeclContext> mds = ctx.methodDecl();
        if(mds!=null){
            for(MethodDeclContext m:mds){
                visit(m);
            }
        }
        MethodNode mm = thisClazz.createMethodNode(Types.VOID_TYPE,"main",Modifier.PUBLIC  + Modifier.STATIC);
        mm.addExceptionType(Types.getExceptionClassType());
        mm.createParameter(Types.getArrayType(Types.getStringClassType()), "args");
        method = mm;
        List<CszzParser.StatContext> stats = ctx.stat();
        if(stats!=null){
            this.methodStatsContexts.put(mm, stats.toArray(new CszzParser.StatContext[stats.size()]));
        }
        AstUtil.createEmptyConstructor(thisClazz);
        return null;
    }
    
    
    public MethodDeclContext getMethodDeclContext(MethodNode mn){
        return this.methodContexts.get(mn);
    }
    
    public void setDiagnosisHandler(DiagnosisHandler diagnosisHandler) {
        this.diagnosisReporter = new DiagnosisReporter(
                this.compilationUnit.getCompileContext(),
                diagnosisHandler, this.compilationUnit.getSource()
        );
    }


}
