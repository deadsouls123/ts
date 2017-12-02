package cszz.compiler;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import cszz.antlr.CszzParser;
import cszz.antlr.CszzParserBaseVisitor;
import cszz.ast.ClassNode;

/**
 *
 * 
 */
public class ClassNodeBuilder extends CszzParserBaseVisitor<Object> {

    private ClassNode topClass;

    private ClassNode thisClazz;
    
    private Map<ClassNode,ParserRuleContext> defContext = new HashMap<ClassNode, ParserRuleContext>();

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
        if (classKind != null) {
            if (classKind.getText().equals("interface")) {
                modifier |= Modifier.ABSTRACT | Modifier.INTERFACE;
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
