package cszz.compiler.codegen;

import static cszz.core.Types.BOOLEAN_TYPE;
import static cszz.core.Types.BYTE_TYPE;
import static cszz.core.Types.CHAR_TYPE;
import static cszz.core.Types.DOUBLE_TYPE;
import static cszz.core.Types.FLOAT_TYPE;
import static cszz.core.Types.INT_TYPE;
import static cszz.core.Types.LONG_TYPE;
import static cszz.core.Types.NULL_TYPE;
import static cszz.core.Types.SHORT_TYPE;
import static cszz.core.Types.VOID_TYPE;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.ACONST_NULL;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ANEWARRAY;
import static org.objectweb.asm.Opcodes.ARRAYLENGTH;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.CHECKCAST;
import static org.objectweb.asm.Opcodes.D2F;
import static org.objectweb.asm.Opcodes.D2I;
import static org.objectweb.asm.Opcodes.D2L;
import static org.objectweb.asm.Opcodes.DCMPL;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.DUP2;
import static org.objectweb.asm.Opcodes.F2D;
import static org.objectweb.asm.Opcodes.F2I;
import static org.objectweb.asm.Opcodes.F2L;
import static org.objectweb.asm.Opcodes.FCMPL;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.I2B;
import static org.objectweb.asm.Opcodes.I2C;
import static org.objectweb.asm.Opcodes.I2D;
import static org.objectweb.asm.Opcodes.I2F;
import static org.objectweb.asm.Opcodes.I2L;
import static org.objectweb.asm.Opcodes.I2S;
import static org.objectweb.asm.Opcodes.IADD;
import static org.objectweb.asm.Opcodes.IALOAD;
import static org.objectweb.asm.Opcodes.IAND;
import static org.objectweb.asm.Opcodes.IASTORE;
import static org.objectweb.asm.Opcodes.IDIV;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.IFGE;
import static org.objectweb.asm.Opcodes.IFGT;
import static org.objectweb.asm.Opcodes.IFLE;
import static org.objectweb.asm.Opcodes.IFLT;
import static org.objectweb.asm.Opcodes.IFNE;
import static org.objectweb.asm.Opcodes.IF_ACMPEQ;
import static org.objectweb.asm.Opcodes.IF_ACMPNE;
import static org.objectweb.asm.Opcodes.IF_ICMPEQ;
import static org.objectweb.asm.Opcodes.IF_ICMPGE;
import static org.objectweb.asm.Opcodes.IF_ICMPGT;
import static org.objectweb.asm.Opcodes.IF_ICMPLE;
import static org.objectweb.asm.Opcodes.IF_ICMPLT;
import static org.objectweb.asm.Opcodes.IF_ICMPNE;
import static org.objectweb.asm.Opcodes.IFNULL;
import static org.objectweb.asm.Opcodes.IFNONNULL;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.IMUL;
import static org.objectweb.asm.Opcodes.INEG;
import static org.objectweb.asm.Opcodes.INSTANCEOF;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.IOR;
import static org.objectweb.asm.Opcodes.IREM;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.ISHL;
import static org.objectweb.asm.Opcodes.ISHR;
import static org.objectweb.asm.Opcodes.ISTORE;
import static org.objectweb.asm.Opcodes.ISUB;
import static org.objectweb.asm.Opcodes.IXOR;
import static org.objectweb.asm.Opcodes.L2D;
import static org.objectweb.asm.Opcodes.L2F;
import static org.objectweb.asm.Opcodes.L2I;
import static org.objectweb.asm.Opcodes.LCMP;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.NEWARRAY;
import static org.objectweb.asm.Opcodes.POP;
import static org.objectweb.asm.Opcodes.POP2;
import static org.objectweb.asm.Opcodes.PUTFIELD;
import static org.objectweb.asm.Opcodes.PUTSTATIC;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.T_BOOLEAN;
import static org.objectweb.asm.Opcodes.T_BYTE;
import static org.objectweb.asm.Opcodes.T_CHAR;
import static org.objectweb.asm.Opcodes.T_DOUBLE;
import static org.objectweb.asm.Opcodes.T_FLOAT;
import static org.objectweb.asm.Opcodes.T_INT;
import static org.objectweb.asm.Opcodes.T_LONG;
import static org.objectweb.asm.Opcodes.T_SHORT;
import static org.objectweb.asm.Opcodes.V1_6;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import cszz.AstNotFoundException;
import cszz.ast.AbstractAstVisitor;
import cszz.ast.AnnotationNode;
import cszz.ast.ArrayLengthExpr;
import cszz.ast.AssignExpr;
import cszz.ast.AstNode;
import cszz.ast.BinaryExpr;
import cszz.ast.BlockStmt;
import cszz.ast.BreakStmt;
import cszz.ast.CastExpr;
import cszz.ast.CatchBlock;
import cszz.ast.ClassNode;
import cszz.ast.ClassReference;
import cszz.ast.CompareExpr;
import cszz.ast.ConstExpr;
import cszz.ast.ContinueStmt;
import cszz.ast.ElementExpr;
import cszz.ast.ErrorousExpr;
import cszz.ast.ExprNode;
import cszz.ast.ExprStmt;
import cszz.ast.FieldExpr;
import cszz.ast.FieldNode;
import cszz.ast.IfStmt;
import cszz.ast.IncrementExpr;
import cszz.ast.InstanceOfExpr;
import cszz.ast.InvocationExpr;
import cszz.ast.LocalVarNode;
import cszz.ast.LogicExpr;
import cszz.ast.LoopStmt;
import cszz.ast.MathExpr;
import cszz.ast.MethodNode;
import cszz.ast.MultiStmt;
import cszz.ast.MultiStmtExpr;
import cszz.ast.NewArrayExpr;
import cszz.ast.NewObjectExpr;
import cszz.ast.ObjectFieldExpr;
import cszz.ast.ObjectInvokeExpr;
import cszz.ast.ParameterExpr;
import cszz.ast.ParameterNode;
import cszz.ast.PrimitiveCastExpr;
import cszz.ast.ReturnStmt;
import cszz.ast.Statement;
import cszz.ast.StaticFieldExpr;
import cszz.ast.StaticInvokeExpr;
import cszz.ast.StoreArrayElementExpr;
import cszz.ast.SuperExpr;
import cszz.ast.ThisExpr;
import cszz.ast.ThrowStmt;
import cszz.ast.TryStmt;
import cszz.ast.UnaryExpr;
import cszz.ast.UnknownFieldExpr;
import cszz.ast.UnknownInvocationExpr;
import cszz.ast.VarDeclStmt;
import cszz.ast.VarExpr;
import cszz.ast.VarObject;
import cszz.compiler.AstLoader;
import cszz.compiler.CodeGenerator;
import cszz.core.ArrayType;
import cszz.core.ClassType;
import cszz.core.ExecutableDescriptor;
import cszz.core.GenericType;
import cszz.core.NullableKind;
import cszz.core.ObjectType;
import cszz.core.PrimitiveType;
import cszz.core.Type;
import cszz.core.Types;
import cszz.core.VarTable;
import cszz.core.WildcardType;
import cszz.exception.Exceptions;
import cszz.tool.OutputManager;
import cszz.util.AstUtil;
import cszz.util.FilePathUtil;
import cszz.util.MethodUtil;
import cszz.util.ModifierUtil;
import cszz.util.NameUtil;
import cszz.util.Parameters;
/**
 *The class generate the java class binary data for ast
 * 
 *  
 */
public class Ast2Class extends AbstractAstVisitor<Object> implements CodeGenerator{
	
    //time is used to account the frequence about CPU.
	public int TIME = 0;
    private ClassWriter classWriter;
    private MethodVisitor md;
    
    OutputManager outputManager;
    
    private Map<Integer,Label> lineLabels = new HashMap();
    
    private Map<VarObject,Integer> varIds = new HashMap<>();
    
    private Stack<Integer> varStartIndexOfFrame = new Stack();
    
    private Map<VarObject,Label> varStartLabels = new HashMap();
    
    private VarTable<Integer,LocalVarNode> varTables = new VarTable();
    
    private int varIdCounter = 0;
    
    private Stack<Label> breakLabels = new Stack<>();
    private Stack<Label> continueLabels = new Stack<>();
    
    private Label methodStartLabel ;
    private Label methodEndLabel;
    private boolean mNullFlag = false;
    
    private final static int 
            T_I = 0,
            T_L = 1,
            T_F = 2,
            T_D = 3,
            T_A = 4;
    private ClassNode clazz;
    private String classInternalName;

    public Ast2Class(OutputManager outputManager) {
        this.outputManager = outputManager;
    }
    
    private int getT(Type type){
        int t;
            if(
                    type.equals(INT_TYPE)
                    ||type.equals(BOOLEAN_TYPE)
                    || type.equals(CHAR_TYPE)
                    || type.equals(BYTE_TYPE)
                    || type.equals(SHORT_TYPE)
                    ){
                t = T_I;
            }else if(type.equals(LONG_TYPE)){
                t = T_L;
            }else if(type.equals(FLOAT_TYPE)){
                t = T_F;
            }else if(type.equals(DOUBLE_TYPE)){
                t = T_D;
            }else{
                t = T_A;
            }
            return t;
    }
    
    @Nullable
    private String classSignature(ClassNode c){
        GenericType[] genericTypes = c.getGenericTypes();
        if(genericTypes==null || genericTypes.length==0){
            return null;
        }
        String gnrTypeStr = "";
        for(GenericType t:genericTypes){
            gnrTypeStr += t.getName() + ":" + "Ljava/lang/Object;";
        }
        String superTypeStr = "";
        if(c.superType!=null) superTypeStr += typeSignature(c.superType);
        for(ObjectType itf:c.getInterfaces()){
            superTypeStr += typeSignature(itf);
        }
        return "<" + gnrTypeStr + ">" + superTypeStr ;
        
    }
    
    private String methodSignature(MethodNode m){
        String ptype = "";
        for(ParameterNode p:m.getParameters()){
            ptype += typeSignature(p.getType());
        }
        return "(" + ptype + ")" + typeSignature(m.getType());
    }
    
    @Nullable
    private String typeSignature(Type type){
        if(type instanceof GenericType){
            return "T" + type.getName() + ";" ;
        }else if(type instanceof ClassType){
            ClassType pt = (ClassType) type;
            String ptypes = "";
            for(Type p:pt.getTypeArguments()){
                ptypes += typeSignature(p);
            }
            if(!ptypes.isEmpty()) ptypes = "<" + ptypes + ">";
            return "L" + pt.getClassNode().name.replace('.', '/') + ptypes + ";";
        }else if(type instanceof PrimitiveType){
            return getTypeDescriptor(type);
        }else if(type instanceof ArrayType){
            return "[" + typeSignature(((ArrayType)type).getComponentType());
        }else if(type instanceof WildcardType){
            WildcardType wt = (WildcardType) type;
            Type[] lbs = wt.getLowerBounds();
            Type[] ubs = wt.getUpperBounds();
            if(lbs.length>0){
                //FIXME handle other lowerBounds
                return "-" + typeSignature(lbs[0]) ;
            }else if(ubs.length>0){
                //FIXME handle other lowerBounds
                return "+" + typeSignature(ubs[0]) ;
            }else{
                return "*";
            }
        }else{
            throw Exceptions.unsupportedTypeException(type);
        }
    }

    private String internalName(String className){
        return className.replace(".", "/");
    }
    
    private String[] internalNames(String[] names){
        String[] inames = new String[names.length];
        for(int i=0;i<names.length;i++){
            inames[i] = internalName(names[i]);
        }
        return inames;
    }
    
    protected String getNullableAnnotation(ObjectType type){
        NullableKind nullable = type.getNullable();
        if(nullable == NullableKind.NONNULL){
            return "cszz.annotation.Nonnull";
        }else if(nullable == NullableKind.NULLABLE){
            return "cszz.annotation.Nullable";
        }else{
            return null;
        }
    }
    
    protected void annotationNullable(Object obj,ObjectType type){
        String annotation = getNullableAnnotation(type);
        if(annotation!=null){
            try {
                annotation(obj, new AnnotationNode(AstLoader.BASE_AST_LOADER.loadAst(annotation)));
            } catch (AstNotFoundException ex) {
                throw Exceptions.missingRuntimeClass(ex.getMessage());
            }
        }
    }
    
    protected void annotation(Object obj,AnnotationNode... annotations){
        for(AnnotationNode an:annotations){
            AnnotationVisitor av;
            String desc = getTypeDescriptor(Types.getClassType(an.getAnnotationType()));
            //TODO set annotation visible
            boolean isVisible = true;
            if(obj instanceof ClassWriter){
                av = ((ClassWriter)obj).visitAnnotation(desc,isVisible);
            }else if(obj instanceof MethodVisitor){
                av = ((MethodVisitor)obj).visitAnnotation(desc, isVisible);
            }else{
                throw Exceptions.unsupportedTypeException(obj);
            }
            for(String v:an.values.keySet()){
                //TODO handle enum value
                Object javaConst = getJavaConst(an.values.get(v));
                av.visit(v, javaConst);
            }
        }
    }
    
    @Override
    public Object visitClassNode(ClassNode node) {        
        ClassNode oldClass = this.clazz;
        this.clazz = node;
        String oldClassInternalName = this.classInternalName;
        this.classInternalName = internalName(clazz);
        ClassWriter oldClassWriter = this.classWriter;
        this.classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        annotation(classWriter, clazz.getAnnotations());
        String parentName = "java.lang.Object";
        ObjectType superType = node.superType;
        if(superType!=null){
            parentName = superType.getName();
        }
        String[] interfaces = null;
        if(node.getInterfaces().length>0){
            interfaces = internalName(node.getInterfaces());
        }
        int access = node.modifier;
        classWriter.visit(V1_6, access,internalName(node.name),classSignature(node), internalName(parentName),interfaces);        String fileName = node.fileName;
        if(fileName!=null && !fileName.isEmpty()){
            classWriter.visitSource(fileName, null);
        }
        visitChildren(node);
        //clinit
        if(!node.staticInitStmts.isEmpty()){
            md = classWriter.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
            visitAll(node.staticInitStmts);
            md.visitInsn(RETURN);
            md.visitMaxs(1, 1);
        }
        
        //md = classWriter.visitMethod(ACC_STATIC, "<iload>", "()V", null, null);

        if(node.enclosingClass!=null){
            this.classWriter.visitInnerClass(this.internalName(node), this.internalName(node.enclosingClass), NameUtil.getSimpleClassName(node.name), node.modifier);
        }
        for(ClassNode ic:node.classes){
            classWriter.visitInnerClass(internalName(ic), internalName(node), NameUtil.getSimpleClassName(ic.name), ic.modifier);
        }
        classWriter.visitEnd();
        if(outputManager!=null){
            try {
            	FileOutputStream fos = new FileOutputStream(new File(FilePathUtil.CLASS_PATH));
            	PrintStream ps = new PrintStream(fos);
                BufferedOutputStream out= new BufferedOutputStream(fos);

                try (OutputStream os = outputManager.createOutputStream(node.name)) {
                    os.write(this.classWriter.toByteArray());
                    fos.write(this.classWriter.toByteArray());
                    System.out.println(this.classWriter.toByteArray());
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }else{
            LOG.log(Level.WARNING, "outputManager is null");
        }
        System.out.println("cpu 周期总数：" + TIME); 
        Runtime runtime = Runtime.getRuntime();
        String[] sh = new String[3];
        String cmd = String.format("javap -c %s > %s", FilePathUtil.CLASS_PATH,FilePathUtil.CMD_PATH);

        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")){
          sh[0] = "cmd";
          sh[1] = "/C";
        } else {
          sh[0] = "/bin/sh";
          sh[1] = "-c";
        }
        sh[2] = cmd;

        try {
			Process process = runtime.exec(sh);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.clazz = oldClass;
        this.classInternalName = oldClassInternalName;
        this.classWriter = oldClassWriter;
        return null;
    }
    private static final Logger LOG = Logger.getLogger(Ast2Class.class.getName());

    @Override
    public Object visitMethodNode(MethodNode node) {
        int access = node.getModifier();
        md = classWriter.visitMethod(access, internalName(node.getName()),getMethodDescriptor(node),methodSignature(node),internalName(node.getExceptionTypes()) );
        if(node.getType() instanceof ObjectType){
            annotationNullable(md,(ObjectType)node.getType());
        }
        annotation(md, node.getAnnotations());
        this.methodStartLabel = new Label();
        this.methodEndLabel = new Label();
        if(AstUtil.isStatic(node.getModifier())){
            varIdCounter = 0;
        }else{
            varIdCounter = 1;
        }
        BlockStmt body = node.getBody();
        ParameterNode[] parameters = node.getParameters();
        for(int i=0;i<parameters.length;i++){
            ParameterNode p = parameters[i];
            visit(p);
            if(p.getType() instanceof ObjectType){
                md.visitParameterAnnotation(i,getClassDescriptor(getNullableAnnotation((ObjectType)p.getType())), true).visitEnd();
            }
        }
        md.visitLabel(methodStartLabel);
        if(body != null){
            visit(body);
            if(node.getType().equals(VOID_TYPE)){
                md.visitInsn(RETURN);
            }
            md.visitLabel(methodEndLabel);
            try{
                md.visitMaxs(0, 0);
            }catch(Exception ex){
                ex.printStackTrace(System.err);
                //throw new RuntimeException("exception when visit method:" + node.name, ex);
            }
        }
        md.visitEnd();
        return null;
    }
    
    private void newFrame(){
        this.varStartIndexOfFrame.push(this.varIdCounter);
        this.varTables = this.varTables.newStack();
    }
    
    private void popFrame(){
        for(LocalVarNode v:this.varTables.values()){
            this.destroyLocalVarNode(v);
        }
        int startVarIdx = this.varStartIndexOfFrame.pop();
        this.varIdCounter = startVarIdx;
        this.varTables = this.varTables.popStack();
    }
    
    private void declareNewVar(VarObject vo){
        int vid = varIdCounter;
        int vSize = asmType(vo.getType()).getSize();
        if(vSize==0){
            throw Exceptions.unexceptedException("");
        }
        varIdCounter+= vSize;
        varIds.put(vo, vid);          
        Label startLabel = new Label();
        md.visitLabel(startLabel);
        this.varStartLabels.put(vo,startLabel);
        if(vo instanceof LocalVarNode){
            this.varTables.put(vid, (LocalVarNode) vo);
        }
    }
    
    private void destroyLocalVarNode(LocalVarNode var){
        Integer vid = this.varIds.get(var);
        //TODO why vid==null
//        if(vid==null){
//            throw Exceptions.unexceptedValue(vid);
//        }
        Label endLabel = new Label();
        md.visitLabel(endLabel);
        this.varIds.remove(var);
        String name = var.getName();
        if(vid!=null && name!=null && !name.isEmpty()){
            md.visitLocalVariable(name, getTypeDescriptor(var.getType()),null ,varStartLabels.get(var), endLabel, vid);
        }
    }

    @Override
    public Object visitBlockStmt(BlockStmt node) {
        this.newFrame();
        visitChildren(node);
        this.popFrame();
        return null;
    }

    @Override
    public Object visitBreakStmt(BreakStmt node) {
    	TIME = TIME+2;
        md.visitJumpInsn(GOTO, breakLabels.peek());
        return null;
    }

    @Override
    public Object visitContinueStmt(ContinueStmt node) {
        md.visitJumpInsn(GOTO, continueLabels.peek());
        return null;
    }
    
    private void pop(Type type){
    	TIME = TIME+2;
        int size =  asmType(type).getSize();
        if(size==1){
            md.visitInsn(POP);
        }else if(size==2){
        	TIME = TIME+4;
            md.visitInsn(POP2);
        }else{
            throw new UnsupportedOperationException("It is unsupported to pop for the type:" + type);
        }
    }

    @Override
    public Object visitExprStmt(ExprStmt node) {
        visitChildren(node);
        Type type = node.getExpr().getType();
        if(type !=null && !Types.VOID_TYPE.equals(type)){
            pop(type);
        }
        return null;
    }
    
    private void ifExpr(boolean jumpOnTrue,ExprNode condition,Label label){
        if(condition instanceof LogicExpr){
            LogicExpr be = (LogicExpr) condition;
            ExprNode e1 = be.getExpr1();
            ExprNode e2 = be.getExpr2();
            String op = be.getOperation();
            switch(op){
                case "&&":
                    if(jumpOnTrue){
                        Label stopLabel = new Label();
                        ifExpr(false,e1,stopLabel);
                        ifExpr(false,e2,stopLabel);
                        md.visitJumpInsn(GOTO, label);
                        md.visitLabel(stopLabel);
                    }else{
                        ifExpr(false, e1, label);
                        ifExpr(false, e2, label);
                    }
                    break;
                case "||":
                    if(jumpOnTrue){
                        ifExpr(true, e1, label);
                        ifExpr(true, e2, label);
                    }else{
                        Label stopLabel = new Label();
                        ifExpr(true, e1, stopLabel);
                        ifExpr(true, e2, stopLabel);
                        md.visitJumpInsn(GOTO, label);
                        md.visitLabel(stopLabel);
                    }
                    break;
                default:
                    throw  new UnsupportedOperationException("Unsupported operation:" + op);
            }
        }else if(condition instanceof CompareExpr){
            ifCompare(jumpOnTrue,((CompareExpr) condition).getExpr1(), ((CompareExpr) condition).getExpr2(), ((CompareExpr) condition).getOperation(), label);
        }else if(condition instanceof UnaryExpr && ((UnaryExpr)condition).getOperation().equals("!")){
            ifExpr(!jumpOnTrue, ((UnaryExpr)condition).getExpr(), label);
        }else{
            visit(condition);
            md.visitJumpInsn(jumpOnTrue ? IFNE : IFEQ, label);
        }
    }

    @Override
    public Object visitIfStmt(IfStmt node) {
        Label stopLabel = new Label();
        Label falseLabel = new Label();
        ExprNode condition = node.getConditionExpr();
        Statement trueBody = node.getTrueBody();
        Statement falseBody = node.getFalseBody();    
        ifExpr(false,condition,falseLabel);
        if(trueBody!=null){
            visit(trueBody);
        }
        if(falseBody==null){
            md.visitLabel(falseLabel);
        }else{
            md.visitJumpInsn(GOTO, stopLabel);
            md.visitLabel(falseLabel);
            visit(falseBody);
        }
        md.visitLabel(stopLabel);
        return null;
    }

    @Override
    public Object visitLoopStmt(LoopStmt node) {
        //visitAll(node.initStmts);
        Label startLabel = new Label();
        Label stopLabel = new Label();
        continueLabels.push(startLabel);
        breakLabels.push(stopLabel);
        md.visitLabel(startLabel);
        if(node.getPreConditionExpr()!=null){
            ifExpr(false, node.getPreConditionExpr(),stopLabel);
        }
        visit(node.getLoopBody());
        if(node.getPostConditionExpr()!=null){
            ifExpr(false, node.getPostConditionExpr(),stopLabel);
        }
        md.visitJumpInsn(GOTO, startLabel);
        md.visitLabel(stopLabel);
        continueLabels.pop();
        breakLabels.pop();
        return null;
    }

    @Override
    public Object visitReturnStmt(ReturnStmt node) {
        int lnsn = RETURN;
        if(node.expr!=null){
            visit(node.expr);
            Type type = node.expr.getType();
            TIME = TIME+4;
            lnsn = asmType(type).getOpcode(IRETURN);
        }
        md.visitInsn(lnsn);
        return null;
    }

    @Override
    public Object visitTryStmt(TryStmt node) {
        Label startLabel = new Label();
        Label endLabel = new Label();
        Label stopLabel = new Label();
        md.visitLabel(startLabel);
        visit(node.getExecStmt());
        md.visitJumpInsn(GOTO, stopLabel);
        md.visitLabel(endLabel);
        if(node.getCatchStmts()!=null){
            for(CatchBlock s:node.getCatchStmts()){
                this.newFrame();
                Label handler = new Label();
                md.visitLabel(handler);
                visit(s);
                md.visitJumpInsn(GOTO, stopLabel);
                String type = asmType(s.catchVar.getType()).getInternalName();
                md.visitTryCatchBlock(startLabel, endLabel, handler,type);
                this.popFrame();
            }
        }
        if(node.getFinallyStmt()!=null){
            this.newFrame();
            Label handler = new Label();
            md.visitLabel(handler);
            visit(node.getFinallyStmt());
            md.visitJumpInsn(GOTO, stopLabel);
            md.visitTryCatchBlock(startLabel, endLabel, handler, null);
            this.popFrame();
        }
        md.visitLabel(stopLabel);
        return null;
    }

    @Override
    public Object visitCatchBlock(CatchBlock node) {
        visit(node.catchVar);
        int exVarId = getVarId(node.catchVar);
        md.visitVarInsn(ASTORE, exVarId);
        visit(node.execStmt);
        mNullFlag = false;
        return null;
    }

    @Override
    public Object visitThrowStmt(ThrowStmt node) {
        visit(node.expr);
        md.visitInsn(ATHROW);
        return null;
    }
    
    private void assignVarObject(VarObject to,ExprNode from){
        org.objectweb.asm.Type type = asmType(to.getType());
        visit(from);
        int vid = getVarId(to);
        TIME++;
        md.visitVarInsn(type.getOpcode(ISTORE), vid);
    }
    
    private void assignField(FieldNode fn,ExprNode target,ExprNode expr){
        int opc = PUTFIELD;
        if (AstUtil.isStatic(fn.modifier)) {
            opc = PUTSTATIC;
        } else {
            visit(target);
        }
        visit(expr);
        md.visitFieldInsn(opc,
                asmType(Types.getClassType(fn.getClassNode())).getInternalName(), fn.getName(), getTypeDescriptor(fn.getType()));
    }
    
    private void assignField(FieldExpr fieldExpr,ExprNode expr){
        if(fieldExpr instanceof StaticFieldExpr){
            assignField(fieldExpr.getField().getFieldNode(), null, expr);
        }else if(fieldExpr instanceof ObjectFieldExpr){
            assignField(fieldExpr.getField().getFieldNode(), ((ObjectFieldExpr) fieldExpr).getTarget(), expr);
        }else{
            throw new UnsupportedOperationException();
        }
    }
    
    private void astore(ExprNode expr){
        visit(expr);
        org.objectweb.asm.Type type = asmType(expr.getType());
        TIME++;
        md.visitInsn(type.getOpcode(IASTORE));
    }
    
    private void assignArrayElement(ExprNode array,ExprNode key,ExprNode value){
        Parameters.requireNonNull(array);
        Parameters.requireNonNull(key);
        Parameters.requireNonNull(value);
        visit(array);
        visit(key);
        astore(value);
    }
    
    private void assign(ExprNode to,ExprNode from){
        if(to instanceof FieldExpr){
            FieldExpr toField = (FieldExpr) to;
            assignField(toField, from);
        }else if(to instanceof VarExpr){
            assignVarObject(((VarExpr) to).getVar(), from);
        }else if(to instanceof ElementExpr){
            ElementExpr elementExpr = (ElementExpr) to;
            assignArrayElement(elementExpr.getArrayExpr(), elementExpr.getIndex(), from);
        }else{
            throw new UnknownError("unknown expression:" + to);
        }
    }

    @Override
    public Object visitAssignExpr(AssignExpr node) {
        assign(node.getTo(), node.getFrom());
        visit(node.getTo());
        return null;
    }

    @Override
    public Object visitBinaryExpr(BinaryExpr node) {
        ExprNode e1 = node.getExpr1();
        ExprNode e2 = node.getExpr2();
        int op;
        org.objectweb.asm.Type at = asmType(node.getExpr1().getType());
        switch(node.getOperation()){
            case "+": 
            	TIME = TIME+2;
            	op = IADD;
            break;
            case "-" :
            	TIME = TIME+2;
            	op = ISUB;
            break;
            case "*" :
            	TIME = TIME+4;
            	op = IMUL;
            break;
            case "/" : 
            	TIME = TIME+8;
            	op = IDIV;
            break;
            case "%":
            	TIME = TIME+1;
            	op = IREM;
            break;
            //bitwise
            case BinaryExpr.OP_AND:
            	TIME = TIME+2;
            	op = IAND;break;
            case BinaryExpr.OP_OR:
            	TIME = TIME+2;
            	op = IOR;break;
            case BinaryExpr.OP_XOR: 
            	TIME = TIME+2;
            	op = IXOR;break;
            case BinaryExpr.OP_SHIFT_LEFT:
            	TIME = TIME+2;
            	op = ISHL;break;
            case BinaryExpr.OP_SHIFT_RIGHT:
            	TIME = TIME+2;
            	op = ISHR;break;
            default://logic expression
                Label trueLabel = new Label();
                Label stopLabel = new Label();
                ifExpr(true,node, trueLabel);
                constFalse();
                md.visitJumpInsn(GOTO, stopLabel);
                md.visitLabel(trueLabel);
                constTrue();
                md.visitLabel(stopLabel);
                return null;
        }
        visit(e1);
        visit(e2);
        md.visitInsn(at.getOpcode(op));
        return null;
    }
    
    protected Object getJavaConst(ConstExpr ce){
        Object v = ce.getValue();
        if(v==null){
            return null;
        }else if(v instanceof ClassReference){
            return asmType(Types.getClassType(((ClassReference) v).getReferencedClassNode()));
        }else{
            Type ct = ce.getType();
            if(
                    Types.isNumber(ct)
                    || Types.isBoolean(ct)
                    || ct.equals(Types.getPrimitiveType(Types.getCharClassType()))
                    || ct.equals(Types.getStringClassType())
                    ){
                return ce.getValue();
            }
            throw Exceptions.unsupportedTypeException(ce);
        }
    }

    @Override
    public Object visitConstExpr(ConstExpr node) {
        Object v = getJavaConst(node);
        if(v==null){
            md.visitInsn(ACONST_NULL);
            mNullFlag = true;
        }else{
        	//if(v instanceof Integer) {
        		//md.visitIntInsn(Opcodes.BIPUSH, (int)v);
        	//} else {
            md.visitLdcInsn(v);
        	//}
        }
        return null;
    }

    @Override
    public Object visitElementExpr(ElementExpr node) {
        visit(node.getArrayExpr());
        visit(node.getIndex());
        org.objectweb.asm.Type t = asmType(node.getType());
        TIME++;
        md.visitInsn(t.getOpcode(IALOAD));
        return null;
    }

    @Override
    public Object visitFieldExpr(FieldExpr node) {
        int   opc ;
        String owner = internalName(node.getField().getFieldNode().getClassNode());
        if(node instanceof ObjectFieldExpr){
            ExprNode target =((ObjectFieldExpr)node).getTarget();
            visit(target);
            opc = GETFIELD;
        }else if(node instanceof StaticFieldExpr){
            opc = GETSTATIC;
        }else{
            throw new UnsupportedOperationException("unsupported field type:" + node);
        }
        md.visitFieldInsn(opc
                ,owner
                , node.getField().getName()
                ,getTypeDescriptor(node.getType()));
        return null;
    }

    @Override
    public Object visitInvocationExpr(InvocationExpr node) {
        int opc;
        ExecutableDescriptor method = node.getMethod();
        String ownerClass;// = internalName(node.getMethod().classNode);
        if (node instanceof StaticInvokeExpr) {
        	TIME = TIME+4;
            opc = INVOKESTATIC;
            ownerClass = internalName(((StaticInvokeExpr) node).getInvokeClass().getReferencedClassNode());
        } else if(node instanceof ObjectInvokeExpr) {
            ObjectInvokeExpr oie = (ObjectInvokeExpr) node;
            ObjectType targetType = (ObjectType) oie.getInvokeTarget().getType();
            ownerClass = internalName(targetType);
            ExprNode target = oie.getInvokeTarget();
            visit(target);
            if (Modifier.isPrivate(method.getModifier()) || (target instanceof SuperExpr) || method.getName().equals("<init>")) {
                opc = INVOKESPECIAL;
            } else {
            	TIME = TIME+6;
                opc =ModifierUtil.isInterface(targetType.getClassNode().modifier) ?
                        INVOKEINTERFACE : INVOKEVIRTUAL;
            }
        }else{
            throw Exceptions.unsupportedTypeException(node);
        }
        visitAll(node.getArguments());
        md.visitMethodInsn(
                opc 
                ,ownerClass
                ,method.getName()
                ,getMethodDescriptor(method.getMethodNode())
        );
        return null;
    }

    @Override
    public Object visitUnaryExpr(UnaryExpr node) {
        Type exprType = node.getExpr().getType();
        org.objectweb.asm.Type t = asmType(exprType);
        visit(node.getExpr());
        switch(node.getOperation()){
            case UnaryExpr.OPERATION_POS:
                break;
            case UnaryExpr.OPERATION_NEG:
            	TIME = TIME+1;
                md.visitInsn(t.getOpcode(INEG));
                break;
            case UnaryExpr.OPERATION_NOT:
                //TODO here I am not sure
                constX(exprType, -1);
                md.visitInsn(t.getOpcode(IXOR));
                break;
                //md.visitInsn(ICONST_M1);
           case UnaryExpr.OPERATION_LOGIC_NOT:
               Label falseLabel = new Label();
               Label stopLabel = new Label();
               TIME = TIME+2;
               md.visitJumpInsn(IFEQ, falseLabel);
               constFalse();
               md.visitJumpInsn(GOTO, stopLabel);
               md.visitLabel(falseLabel);
               constTrue();
               md.visitLabel(stopLabel);
               break;
           default:
               throw new UnsupportedOperationException("unsupported unary operation:" + node.getOperation());
        }
        return null;
    }

    @Override
    public Object visitVarExpr(VarExpr node) {
        visitVarObject(node.getVar());
        return null;
    }

    @Override
    public Object visitParameterExpr(ParameterExpr node) {
        visitVarObject(node.getParameter());
        return null;
    }

    @Override
    public Object visitCastExpr(CastExpr node) {
        visit(node.getExpr());
        md.visitTypeInsn(CHECKCAST, internalName(node.getToType()));
        return null;
    }

    @Override
    public Object visitNewArrayExpr(NewArrayExpr node) {
        visit(node.getSize());
        Type t = node.getComponentType();
        int opr = -1;
        int op = NEWARRAY;
        if(t.equals(BOOLEAN_TYPE)){
            opr = T_BOOLEAN;
        }else if(t.equals(CHAR_TYPE)){
            opr = T_CHAR;
        }else if(t.equals(SHORT_TYPE)){
            opr = T_SHORT;
        }else if(t.equals(INT_TYPE)){
            opr = T_INT;
        }else if(t.equals(LONG_TYPE)){
            opr = T_LONG;
        }else if(t.equals(FLOAT_TYPE)){
            opr = T_FLOAT;
        }else if(t.equals(DOUBLE_TYPE)){
            opr = T_DOUBLE;
        }else if(t.equals(BYTE_TYPE)){
            opr = T_BYTE;
        }else{
            op = ANEWARRAY;
        }
        if(op==NEWARRAY){
            md.visitIntInsn(op, opr);
        }else{
            md.visitTypeInsn(ANEWARRAY, internalName(t));
        }
        return null;
    }

    @Override
    public Object visitThisExpr(ThisExpr node) {
        md.visitVarInsn(ALOAD, 0);
        return null;
    }

    @Override
    public Object visitMultiStmtExpr(MultiStmtExpr node) {
        visitAll(node.stmts);
        visit(node.reference);
        return null;
    }
    
    private String getTypeDescriptor(Type[] types){
        if(types==null) return null;
        if(types.length==0) return null;
        String ts = "";
        for(Type t:types){
            ts += getTypeDescriptor(t);
        }
        return ts;
    }
    
    private String getTypeDescriptor(Type t){
        if(t instanceof PrimitiveType){
            if(t.equals(VOID_TYPE)){
                return "V";
            }else if(t.equals(BOOLEAN_TYPE)){
                return "Z";
            }else if(t.equals(LONG_TYPE)){
                return "J";
            }else if(t.equals(INT_TYPE)){
                return "I";
            }else if(t.equals(CHAR_TYPE)){
                return "C";
            }else if(t.equals(SHORT_TYPE)){
                return "S";
            }else if(t.equals(BYTE_TYPE)){
                return "B";
            }else if(t.equals(FLOAT_TYPE)){
                return "F";
            }else if(t.equals(DOUBLE_TYPE)){
                return "D";
            }else if(t.equals(NULL_TYPE)){
                return "Ljava/lang/Object;";
            }else{
                throw Exceptions.unsupportedTypeException(t);
            }
        }else if(t instanceof ArrayType){
            return "[" + getTypeDescriptor(((ArrayType)t).getComponentType());
        }else if(t instanceof GenericType){
            return getTypeDescriptor(((GenericType) t).getSuperType());
        }else if(t instanceof ClassType){
            return "L" + internalName(((ClassType) t).getClassNode().name) + ";";
        }else if(t instanceof WildcardType){
            return getTypeDescriptor(((WildcardType) t).getSuperType());
        }else{
            throw Exceptions.unsupportedTypeException(t);
        }
    }
    
    private String getClassDescriptor(String className){
        return "L" + internalName(className) + ";" ;
    }
    
    private String getMethodDescriptor(Type returnType,Type[] parameterTypes){
        String desc = "";
        String retTyp = getTypeDescriptor(returnType);
        if(parameterTypes!=null){
            for(Type t:parameterTypes){
                desc += getTypeDescriptor(t);
            }
        }
        return "(" + desc + ")" + retTyp;     
    }
        
    private String getMethodDescriptor(MethodNode node) {
        return getMethodDescriptor(node.getType(), MethodUtil.getParameterTypes(node));
    }
    
    private org.objectweb.asm.Type asmType(Type type){
        String typeDesc = getTypeDescriptor(type);
        return org.objectweb.asm.Type.getType(typeDesc);
    }

    private int getVarId(VarObject var) {
        Integer vid = varIds.get(var);
        if(vid==null){
            throw new UnknownError("unknown var:" + var);
        }
        return vid;
    }
    
    private void visitVarObject(VarObject vo){
        org.objectweb.asm.Type type = asmType(vo.getType());
        int vid = getVarId(vo);
        TIME++;
        md.visitVarInsn(type.getOpcode(ILOAD),vid);
    }
    
    @Nonnull
    private String[] internalName(@Nonnull ClassNode[] clazz){
        String[] names = new String[clazz.length];
        for(int i=0;i<clazz.length;i++){
            names[i] = internalName(clazz[i]);
        }
        return names;
    }
    
    private String internalName(ClassNode clazz){
        return internalName(Types.getClassType(clazz));
    }

    private String internalName(Type t) {
        org.objectweb.asm.Type asmType = asmType(t);
        Objects.requireNonNull(asmType, "couldn't get asm type for " + t);
        try{
            return asmType.getInternalName();
        }catch(Exception ex){
            throw new RuntimeException("couldn't get asm type for " + t);
        }
    }
    
    private String[] internalName(Type[] types){
        String[] ts = new String[types.length];
        for(int i=0;i<types.length;i++){
            ts[i] = internalName(types[i]);
        }
        return ts;
    }
    
    @Override
    public void generate(ClassNode classNode){
        visitClassNode(classNode);
    }

    @Override
    public Object visitVarDeclStmt(VarDeclStmt node) {
        return visitChildren(node);
    }
    
    private int getPrimitiveCastOpc(Type fromType,Type toType){
        
        Type f = fromType;
        Type tt = toType;
        if(f.equals(INT_TYPE)){
            if(tt.equals(LONG_TYPE)) return I2L;
            else if(tt.equals(FLOAT_TYPE)) return I2F;
            else if(tt.equals(DOUBLE_TYPE)) return I2D;
            else if(tt.equals(SHORT_TYPE)) return I2S;
            else if(tt.equals(BYTE_TYPE)) return I2B;
            else if(tt.equals(CHAR_TYPE)) return I2C;
        }else if(f.equals(FLOAT_TYPE)){
            if(tt.equals(INT_TYPE)) {
            	TIME = TIME+2;
            	return F2I;
            }                  	
            else if(tt.equals(LONG_TYPE)) return F2L;
            else if(tt.equals(DOUBLE_TYPE)) return F2D;
        }else if(f.equals(LONG_TYPE)){
            if(tt.equals(INT_TYPE)) return L2I;
            else if(tt.equals(FLOAT_TYPE)) return L2F;
            else if(tt.equals(DOUBLE_TYPE)) return L2D;
        }else if(f.equals(DOUBLE_TYPE)){
            if(tt.equals(INT_TYPE)) {
            	TIME = TIME+2;
            	return D2I;
            }
            else if(tt.equals(LONG_TYPE)) return D2L;
            else if(tt.equals(FLOAT_TYPE)) return D2F;
        }else if(f.equals(BYTE_TYPE)){
            if(tt.equals(SHORT_TYPE)) return 0;
            else if(tt.equals(INT_TYPE)) return 0;
            else if(tt.equals(LONG_TYPE)) return I2L;
            else if(tt.equals(FLOAT_TYPE)) return I2F;
            else if(tt.equals(DOUBLE_TYPE)) return I2D;
        }else if(f.equals(CHAR_TYPE) || f.equals(SHORT_TYPE)){
            if(tt.equals(INT_TYPE)) return 0;
            else if(tt.equals(LONG_TYPE)) return I2L;
            else if(tt.equals(FLOAT_TYPE)) return I2F;
            else if(tt.equals(DOUBLE_TYPE)) return I2D;
        }
        throw Exceptions.unexceptedException("It is unable to cast " + fromType + " to " + toType);
    }

    @Override
    public Object visitPrimitiveCastExpr(PrimitiveCastExpr node) {
        ExprNode expr = node.getExpr();
        visit(expr);
        int opc;
        Type ft = expr.getType();
        Type tt = node.getToType();
        opc = getPrimitiveCastOpc(ft, tt);
        if(opc>0){
            md.visitInsn(opc);
        }
        return null;
    }

    @Override
    public Object visitLocalVarNode(LocalVarNode localVarNode) {
        this.declareNewVar(localVarNode);
        return null;
    }

    @Override
    public Object visitParameterNode(ParameterNode parameterNode) {
        md.visitParameter(parameterNode.getName(), parameterNode.modifier);
        this.declareNewVar(parameterNode);
        return null;
    }

    @Override
    public Object visitFieldNode(FieldNode fieldNode) {
        classWriter.visitField(fieldNode.modifier, fieldNode.getName(), getTypeDescriptor(fieldNode.getType()), null, null);
        return null;
    }

    @Override
    public Object visitNewObjectExpr(NewObjectExpr node) {
        org.objectweb.asm.Type t = asmType(node.getObjectType());
        md.visitTypeInsn(NEW, t.getInternalName());
        TIME = TIME+2;
        md.visitInsn(DUP);
        visitAll(node.getConstructor().getArguments());
        TIME = TIME+4;
        md.visitMethodInsn(
                INVOKESPECIAL
                , t.getInternalName()
                , "<init>"
                ,getMethodDescriptor(node.getConstructor().getMethod().getMethodNode())
                , false);
        return null;
    }

    private void dupX(Type type){
        int size = asmType(type).getSize();
        if(size==1) { 
            TIME = TIME+2;
            md.visitInsn(DUP);
        }else if(size==2) {
            TIME = TIME+3;
            md.visitInsn(DUP2);
        }else {
            throw new UnsupportedOperationException("unsupported type:" + type);
        }
    }
    
    @Override
    public Object visitIncrementExpr(IncrementExpr node) {
        if(!node.isIsPrefix()){
            visit(node.getExpr());
        }
        Type exprType = node.getExpr().getType();
        ConstExpr ce = getConstX(exprType, node.isIsDesc() ? -1 : 1);
        BinaryExpr be = new MathExpr(node.getExpr(),ce, "+");
        AssignExpr addOne = new AssignExpr(node.getExpr(),be);
        visit(addOne);
        pop(exprType);
        if(node.isIsPrefix()){
            visit(node.getExpr());
        }        
        return null;
    }
    
    private ConstExpr getConstX(Type type, int i) {
        Object obj;
        int t = getT(type);
        switch (t) {
            case T_I:
                obj = new Integer(i);
                break;
            case T_L:
                obj = new Long(i);
                break;
            case T_F:
                obj = new Float(i);
                break;
            case T_D:
                obj = new Double(i);
                break;
            default:
                throw new UnsupportedOperationException("unsupported type:" + type);
        }
        return new ConstExpr(obj);
    }
    private void constX(Object x){
        md.visitLdcInsn(x);
    }

    private void constX(Type type,int i) {
        constX(getConstX(type, i).getValue());
    }

    @Override
    public Object visit(AstNode node) {
        int lineNum = node.offset.startLine;
        if(lineNum>0 && (node instanceof Statement || node instanceof ExprNode) &&  !lineLabels.containsKey(lineNum)){
            Label lb = new Label();
            md.visitLabel(lb);
            md.visitLineNumber(lineNum, lb);
            lineLabels.put(lineNum, lb);
        }
        return super.visit(node);
    }

    @Override
    public Object visitArrayLengthExpr(ArrayLengthExpr node) {
        visit(node.getArrayExpr());
        md.visitInsn(ARRAYLENGTH);
        return null;
    }
    
    private void constTrue(){
        constX(1);
    }
    
    private void constFalse(){
        constX(0);
    }

    @Override
    public Object visitUnknownFieldExpr(UnknownFieldExpr node) {
        throw new UnsupportedOperationException("BUG:invoking unknown method:" + node.getFieldName());
    }

    @Override
    public Object visitUnknownInvocationExpr(UnknownInvocationExpr node) {
        throw new UnsupportedOperationException("BUG:invoking unknown method:" + node.getMethodName());
    }

    @Override
    public Object visitClassReference(ClassReference node) {
        //do nothing
        return null;
    }

    @Override
    public Object visitSuperExpr(SuperExpr node) {
        md.visitVarInsn(ALOAD, 0);
        return null;
    }

    @Override
    public Object visitErrorousExpr(ErrorousExpr node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visitInstanceOfExpr(InstanceOfExpr node) {
        visit(node.getExpr());
        md.visitTypeInsn(INSTANCEOF, internalName(node.getTarget().getReferencedClassNode()));
        return null;
    }

    private void ifCompare(boolean jumpOnTrue,ExprNode expr1, ExprNode expr2, String op, Label label) {
        Type type = expr1.getType();
        visit(expr1);
        visit(expr2);
        int t = getT(type);
        if(T_I == t){
            int opc = -1;
            switch(op){
                case "==" :
                	TIME = TIME+2;
                    opc = jumpOnTrue ? IF_ICMPEQ : IF_ICMPNE;
                	//opc = IFNULL;
                    break;
                case ">"    : 
                    opc =jumpOnTrue ? IF_ICMPGT : IF_ICMPLE;
                    break;
                case ">=" : 
                	TIME = TIME+2;
                    opc =jumpOnTrue ? IF_ICMPGE : IF_ICMPLT;
                    break;
                case "<"   : 
                    opc = jumpOnTrue ? IF_ICMPLT : IF_ICMPGE;
                    break;
                case "<=" : 
                	TIME = TIME+2;
                    opc =jumpOnTrue ? IF_ICMPLE : IF_ICMPGT;
                    break;
                case "!=" : 
                	TIME = TIME+2;
                    opc = jumpOnTrue ? IF_ICMPNE : IF_ACMPEQ;
                    break;
                default:
                    throw  new UnsupportedOperationException("Unsupported operation:" + op);
            }
            md.visitJumpInsn(opc, label);
        }else if(T_A==t){//object type
             if(op.equals("==")){
            	if(mNullFlag) {
            		md.visitJumpInsn(jumpOnTrue ? IFNULL: IFNONNULL,label);
            	} else {
                    md.visitJumpInsn(jumpOnTrue ? IF_ACMPEQ : IF_ACMPNE,label);
            	}
            }else if(op.equals("!=")){
            	if(mNullFlag) {
            		md.visitJumpInsn(jumpOnTrue ? IFNONNULL : IFNULL,label);
            	} else {
                    md.visitJumpInsn(jumpOnTrue ? IF_ACMPNE:IF_ACMPEQ,label);
            	}
            }else{
                throw new UnsupportedOperationException("It is unsupported to compare object type:" + type);
            }
        }else{//type is not int,not object            
            if(T_L==t){
                md.visitInsn(LCMP);
            }else if(T_F==t){
                md.visitInsn(FCMPL);
            }else if(T_D==t){
                md.visitInsn(DCMPL);
            }else{
               throw new UnsupportedOperationException("It is unsupported to compare object type:" + type);
            }
            int opc = -1;
            switch(op){
                case "==" : opc =jumpOnTrue ? IFEQ : IFNE;break;
                case ">"    : opc =jumpOnTrue ? IFGT : IFLE ;break;
                case ">=" : 
                	TIME = TIME+2;
                	opc =jumpOnTrue ? IFGE : IFLT ;
                break;
                case "<"   : 
                	TIME = TIME+2;
                	opc =jumpOnTrue ? IFLT:IFGE;break;
                case "<=" : 
                	TIME = TIME+2;
                	opc =jumpOnTrue ? IFLE:IFGT;break;
                case "!=" : 
                	TIME = TIME +2;
                	opc =jumpOnTrue ? IFNE:IFEQ;break;
                default:
                    throw  new UnsupportedOperationException("Unsupported operation:" + op);
            }
            md.visitJumpInsn(opc, label);
        }
    }

    @Override
    public Object visitMultiStmt(MultiStmt node) {
        visitAll(node.statements);
        return null;
    }

    @Override
    public Object visitStoreArrayElementExpr(StoreArrayElementExpr node) {
        md.visitVarInsn(ALOAD, this.getVarId(node.getArray()));
        visit(node.getIndex());
        astore(node.getValueExpr());
        return null;
    }

}
