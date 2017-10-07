package cszz.compiler;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import cszz.antlr.CszzParser;
import cszz.antlr.CszzParserBaseVisitor;
import cszz.ast.AssignExpr;
import cszz.ast.AssignableExpr;
import cszz.ast.BlockStmt;
import cszz.ast.ClassNode;
import cszz.ast.ExprNode;
import cszz.ast.ExprStmt;
import cszz.ast.FieldNode;
import cszz.ast.MethodNode;
import cszz.ast.ParameterExpr;
import cszz.ast.ParameterNode;
import cszz.ast.Statement;
import cszz.ast.ThisExpr;
import cszz.core.GenericType;
import cszz.core.ModifierConstant;
import cszz.core.NullableKind;
import cszz.core.ObjectType;
import cszz.core.Types;
import cszz.exception.Exceptions;
import cszz.util.AstUtil;
import cszz.util.ModifierUtil;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

/**
 *
 * 
 */
public class ClassNodeBuilder extends CszzParserBaseVisitor<Object> {

    private ClassNode topClass;

    private ClassNode thisClazz;
    
    private Map<ClassNode,ParserRuleContext> defContext = new HashMap();

    private boolean inScriptMode = false;
    
    private boolean isScript = false;

    AstBuilder astBuilder;
    private final CompilationUnit compilationUnit;
    private DiagnosisReporter diagnosisReporter;

    public ClassNodeBuilder(CompilationUnit compilationUnit, AstBuilder astBuilder) {
        this.compilationUnit = compilationUnit;
        this.astBuilder = astBuilder;
    }
    
    public ClassNode build(CszzParser.CompilationUnitContext ctx){
        this.visitCompilationUnit(ctx);
        return topClass;
    }

    @Override
    public Object visitScriptDef(CszzParser.ScriptDefContext ctx) {
        this.isScript = true;
        this.inScriptMode = true;
        //FIXME fix fileName
        //thisClazz.fileName = this.compilationUnit.getSource().getFileName();
        String className = astBuilder.getClassName();
        int modifier = Modifier.PUBLIC;
        topClass = thisClazz = new ClassNode(className, modifier);
        this.defContext.put(topClass, ctx);
        super.visitScriptDef(ctx);
        return null;
    }    

    @Override
    public Object visitClassDef(CszzParser.ClassDefContext ctx) {
        ClassNode oldClass = thisClazz;
        Token nameIdentifier = ctx.name;
        int modifier = astBuilder.parseModifier(ctx.varModifier());
        if (inScriptMode) {
            modifier |= Modifier.STATIC;
        }
        Token classKind = ctx.classKind;
        boolean isInterface = false;
        if (classKind != null) {
            if (classKind.getText().equals("interface")) {
                modifier |= Modifier.ABSTRACT | Modifier.INTERFACE;
                isInterface = true;
            }
        }
        String classDefName;
        if (oldClass != null) {
            if (nameIdentifier == null) {
                diagnosisReporter.report(Diagnosis.Kind.ERROR,"Identifier excepted", ctx);
                return null;
            }
            classDefName = oldClass.name + "$" + nameIdentifier.getText();
        } else {
            classDefName = astBuilder.getClassName();
        }
        ClassNode theClass = thisClazz = new ClassNode(classDefName, modifier);
        astBuilder.thisClazz = thisClazz;
        this.defContext.put(theClass, ctx);
        if (oldClass == null) {
            this.topClass = theClass;
        } else {
            oldClass.classes.add(thisClazz);
            thisClazz.enclosingClass = oldClass;
        }
        //FIXME fix file name
        //thisClazz.fileName = this.compilationUnit.getSource().getFileName();
        boolean oldScriptMode = this.inScriptMode;
        this.inScriptMode = false;
        visit(ctx.classBody());
        this.inScriptMode = oldScriptMode;
        astBuilder.mapAst(thisClazz, ctx);
        thisClazz = oldClass;
        return null;
    }

    private boolean isNonStaticInnerClass(ClassNode clazz) {
        return clazz.enclosingClass != null && !Modifier.isStatic(clazz.modifier);
    }

    private boolean isDeclaringNonStaticInnerClass() {
        return isNonStaticInnerClass(thisClazz);
    }
    
    public ClassNode getClassNode(){
        return topClass;
    }
    
    public ParserRuleContext getClassNodeDefContext(ClassNode classNode){
        return this.defContext.get(classNode);
    }

    public boolean isScript() {
        return isScript;
    }
    
    public void setDiagnosisHandler(DiagnosisHandler diagnosisHandler){
        this.diagnosisReporter = new DiagnosisReporter(
                this.compilationUnit.getCompileContext()
                , diagnosisHandler, this.compilationUnit.getSource());
    }
    
}
