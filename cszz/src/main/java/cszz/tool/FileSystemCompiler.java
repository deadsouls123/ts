package cszz.tool;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nullable;

import org.apache.commons.io.FileUtils;

import cszz.ast.ClassNode;
import cszz.compiler.AstLoader;
import cszz.compiler.CodeGenerator;
import cszz.compiler.CompilationUnit;
import cszz.compiler.CompilePhase;
import cszz.compiler.CszzCompiler;
import cszz.compiler.JavaAstLoader;
import cszz.compiler.SourceLoader;
import cszz.compiler.codegen.Ast2JavaStub;
import cszz.util.ClassNameUtil;

/**
 * The FileSystemCompiler compile sources from file system.
 * 
 * 
 */
public class FileSystemCompiler extends CszzCompiler{

    private Map<String, File> sourceFiles = new HashMap<>();

    protected List<URL> classPaths = new LinkedList<>();
    
    @Nullable
    protected MultiClassLoader classLoader;
    
    protected  final List<File> sourcePaths = new LinkedList<>();
    
    @Deprecated
    public void addClassLoader(ClassLoader classLoader){
        getClassLoader().addClassLoader(classLoader);
    }
    
    public MultiClassLoader getClassLoader(){
        //TODO bug?
        if(classLoader == null){
            URLClassLoader pathClassLoader = new URLClassLoader(classPaths.toArray(new URL[classPaths.size()]));
            classLoader = new MultiClassLoader(pathClassLoader);
        }
        return classLoader;
    }
    
    private CodeGenerator codeGenerator;

    public FileSystemCompiler() {
        super();
    }
    
    @Override
    public AstLoader getAstLoader() {
        return new JavaAstLoader(super.getAstLoader(),getClassLoader());
    }

    @Override
    public CodeGenerator createCodeGenerator(CompilationUnit compilationUnit) {
        return new CodeGenerator() {
            @Override
            public void generate(ClassNode classNode) {
                if (codeGenerator != null) {
                    codeGenerator.generate(classNode);
                }
            }

        };
    }

    public void addSource(File srcDir, File file) throws IOException {
        String className = ClassNameUtil.getClassName(srcDir, file);
        sourceFiles.put(className, file);
        super.addSource(className, FileUtils.readFileToString(file),file.getName());
    }

    public void addSourceDir(File sourceDir) throws IOException {
        Collection<File> files = FileUtils.listFiles(sourceDir, new String[]{"kl"}, true);
        for (File f : files) {
            addSource(sourceDir , f);
        }
    }

    public void addClassPath(File path) {
        try {
            classPaths.add(path.toURI().toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(FileSystemCompiler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void generateJavaStub(OutputManager om) {
        //TODO here has a bug.It will ignore class reference in method body
        super.compile(CompilePhase.PHASE_PARSING);
        HashMap<String, CompilationUnit> sourceAsts = getCompilationUnits();
        for(Map.Entry<String, CompilationUnit> a:sourceAsts.entrySet()){
            Ast2JavaStub a2js = new Ast2JavaStub();
            a2js.generate(a.getValue().getAst());
            String stubCode = a2js.getJavaStubCode();
            String className = (a.getValue().getAst().name);
            if(om==null){
                System.out.println(stubCode);
            }else{                
                try {
                    OutputStream os = om.createOutputStream(className);
                    //TODO should set encoding?
                    os.write(stubCode.getBytes());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
                
        }
    }   

    @Override
    public SourceLoader getSourceLoader() {
        return new FileSystemSourceLoader(sourcePaths.toArray(new File[sourcePaths.size()]),new String[]{"kl","cszz"});
    }
    
    public void addSourcePath(File path){
        sourcePaths.add(path);
    }

    public CodeGenerator getCodeGenerator() {
        return codeGenerator;
    }

    public void setCodeGenerator(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }
    
    protected void handleIOException(IOException ex){
        ex.printStackTrace(System.err);
    }

}
