package kalang.compiler.codegen;

import java.lang.reflect.Modifier;
import kalang.ast.AbstractAstVisitor;
import kalang.ast.AstVisitor;
import kalang.ast.ClassNode;
import kalang.ast.FieldNode;
import kalang.ast.MethodNode;
import kalang.ast.ParameterNode;
import kalang.compiler.CodeGenerator;
import kalang.core.GenericType;
import kalang.util.AstUtil;
import kalang.util.NameUtil;

/**
 *
 * @author Kason Yang
 */
public class Ast2JavaStub extends AstVisitor<Void> implements CodeGenerator{
    
    protected StringBuilder sb;
    
    private boolean isInterface = false;
    
    private static String getJavaTypeName(String typeName){
        String type = typeName.replace('$', '.');
        if(type.endsWith("?")){
            return type.substring(0,type.length()-1);
        }else{
            return type;
        }
    }

    @Override
    public Void visitFieldNode(FieldNode fieldNode) {
        sb.append(modifier2String(fieldNode.modifier))
                .append(" ")
                .append(getJavaTypeName(fieldNode.getType().getName()))
                .append(" ")
                .append(fieldNode.getName())
                .append(";\n");
        return super.visitFieldNode(fieldNode);
    }

    @Override
    public Void visitMethodNode(MethodNode node) {
        sb.append(modifier2String(node.getModifier()))
                .append(" ");
        boolean isConstructor = "<init>".equals(node.getName());
        if(isConstructor){
            sb.append(NameUtil.getSimpleClassName(node.getClassNode().name));
        }else{
            sb.append(this.isInterface ? "" : "native ")
                .append(getJavaTypeName(node.getType().getName()))
                .append(" ")
                .append(node.getName());
        }
        sb.append("(");
        super.visitMethodNode(node);
        sb.append(")");
        if(isConstructor){
            sb.append("{}");
        }else{
            sb.append(";");
        }
        sb.append('\n');
        return null;
    }

    @Override
    public Void visitParameterNode(ParameterNode parameterNode) {
        char last = sb.charAt(sb.length()-1);
        if(last!='('){
            sb.append(",");
        }
        sb.append(getJavaTypeName(parameterNode.getType().getName()))
                .append(" ")
                .append(parameterNode.getName());
        return super.visitParameterNode(parameterNode);
    }
    
    
    private String modifier2String(int modifier){
        if(this.isInterface){
            modifier &= ~ Modifier.ABSTRACT;
        }
        return Modifier.toString(modifier);
    }
    

    @Override
    public Void visitClassNode(ClassNode node) {
        //TODO maybe inner class
        this.isInterface = Modifier.isInterface(node.modifier);
        String clsName = node.name;
        boolean isInnerClass = node.enclosingClass!=null;
        if(!isInnerClass){
            String pkgName = NameUtil.getPackageName(clsName);
            if(pkgName!=null){
                sb.append("package ").append(pkgName).append(";\n");
            }
        }
        sb.append(modifier2String(node.modifier))
                .append(this.isInterface ? " " : " class ")        
                .append(NameUtil.getSimpleClassName(clsName));
        GenericType[] genTypes = node.getGenericTypes();
        if(genTypes.length>0){
            String[] genTypeStrs = new String[genTypes.length];
            for(int i=0;i<genTypeStrs.length;i++){
                genTypeStrs[i] = genTypes[i].getName();
            }
            sb.append("<")
                    .append(String.join(",", genTypeStrs))
                    .append(">");
        }
        sb.append("{\n");
        super.visitClassNode(node);
        sb.append("\n}");
        return null;
    }
    
    public String getJavaStubCode(){
        return sb.toString();
    }

    @Override
    public void generate(ClassNode classNode) {
        sb = new StringBuilder();
        visit(classNode);
    }
    
}
