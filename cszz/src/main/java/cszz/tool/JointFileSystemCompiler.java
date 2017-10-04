package cszz.tool;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import cszz.ast.ClassNode;
import cszz.AstNotFoundException;
import cszz.compiler.CompilePhase;
import cszz.compiler.JavaAstLoader;
import cszz.compiler.CszzSource;
import cszz.java.MemoryCompiler;
import cszz.java.MemoryFileManager;
import cszz.java.StringJavaSource;
import cszz.java.StringJavaSourceBase;
import cszz.util.ClassNameUtil;
import cszz.util.FilePathUtil;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Kason Yang
 */
public class JointFileSystemCompiler extends FileSystemCompiler{
    
    final Map<String,JavaFileObject> javaFiles = new HashMap<>();
    
    final List<File> javaSourcePath = new LinkedList<>();
    
    final MemoryOutputManager javaStubManager = new MemoryOutputManager();
    private MemoryCompiler javaCompiler;
    private OutputManager javaOutputManager;
    
    public void addJavaSourcePath(File path){
        javaSourcePath.add(path);
    }
   
    public void addJavaSourceDir(File sourceDir) throws IOException {
        Collection<File> files = FileUtils.listFiles(sourceDir, new String[]{"java"}, true);
        if(files!=null){
            for(File f:files){
                addJavaSource(sourceDir, f);
            }
        }
    }
    
    public void addCszzOrJavaSource(File dir,File file) throws IOException{
        if(file.getName().endsWith(".java")){
            addJavaSource(dir, file);
        }else{
            addSource(dir, file);
        }
    }
    
    public void addCszzAndJavaSourceDir(File srcDir) throws IOException{
        addSourceDir(srcDir);
        addJavaSourceDir(srcDir);
    }
    
    public void addJavaSource(File srcDir,File file) throws IOException{
        String className = ClassNameUtil.getClassName(srcDir, file);
        javaFiles.put(className,StringJavaSource.loadFromFile(srcDir, file));
    }
    
    private ClassNode createMockClass(String className){
        ClassNode node = new ClassNode(className,Modifier.PUBLIC);
        return node;
    }

    @Override
    public ClassNode loadAst(String className) throws AstNotFoundException {
        try{
            return super.loadAst(className);
        }catch(AstNotFoundException ex){
            try {
                JavaFileObject js = loadJavaSource(className);
                if(js==null) throw ex;
                if(getCurrentCompilePhase()<CompilePhase.PHASE_BUILDAST){
                    return createMockClass(className);
                }
                if(javaCompiler==null) throw ex;
                JavaAstLoader javaAstLoader = new JavaAstLoader(null,javaCompiler);
                //TODO the class would not write to disk
                return javaAstLoader.loadAst(className);
            } catch (IOException ioEx) {
                handleIOException(ioEx);
                throw ex;
            }
        }
    }

    @Override
    public void compile() {
        generateJavaStub(javaStubManager);
        JointFileSystemCompiler that = this;
        javaCompiler = new MemoryCompiler(){
            @Override
            protected JavaFileObject loadJavaSource(String className) throws IOException {
                JavaFileObject js = super.loadJavaSource(className);
                if(js!=null){
                    return js;
                }
                return that.loadJavaSource(className);
            }

            @Override
            protected MemoryFileManager createFileManager(StandardJavaFileManager sfm) {
                MemoryFileManager superFm = super.createFileManager(sfm);
                return new MemoryFileManager(superFm){
                    @Override
                    public Iterable<JavaFileObject> list(JavaFileManager.Location location, String packageName, Set<JavaFileObject.Kind> kinds, boolean recurse) throws IOException {
                        Iterable<JavaFileObject> superList = super.list(location, packageName, kinds, recurse);
                        List<JavaFileObject> files = new LinkedList<>();
                        if(superList!=null){
                            superList.forEach(i -> files.add(i));
                        }
                        for(File p:JointFileSystemCompiler.this.sourcePaths){
                            File dir = packageName !=null && !packageName.isEmpty()
                        ? new File(p,packageName.replace('.', '/'))
                        : p;
                            if(dir.exists() && dir.isDirectory()){
                                Collection<File> klFiles = FileUtils.listFiles(dir, new String[]{"kl","cszz"}, recurse);
                                klFiles.forEach(f -> {
                                    String className = ClassNameUtil.getClassName(p, f);
                                    files.add(
                                        new StringJavaSourceBase(className){
                                            @Override
                                            protected CharSequence getContent() {
                                                try {
                                                    return loadJavaSource(className).getCharContent(true);
                                                } catch (IOException ex) {
                                                    JointFileSystemCompiler.this.handleIOException(ex);
                                                    return "";
                                                }
                                            }
                                        }
                                    );
                                });
                            }
                        }
                        return files;
                    }
                    
                };
            }

        };
        for(URL p:classPaths){
            javaCompiler.addClassPath(p);
        }
        for(File p : javaSourcePath){
            javaCompiler.addSourcePath(p);
        }
        for(Map.Entry<String, JavaFileObject> e:javaFiles.entrySet()){
            javaCompiler.addSource(e.getValue());
        }
        String[] stubNames = javaStubManager.getClassNames();
        for(String n:stubNames){
            String code = new String(javaStubManager.getBytes(n));
            javaCompiler.addSourceFromString(n, code);
        }
        if(!javaCompiler.compile()){
            DiagnosticCollector<JavaFileObject> dc = javaCompiler.getDiagnosticCollector();
            for(Diagnostic<? extends JavaFileObject> d : dc.getDiagnostics()){
                handleJavaDiagnostic(d);
            }
            return;
        }
        MemoryFileManager fm = javaCompiler.getFileManager();
        Map<String, byte[]> bytes = null;
        if(fm!=null) bytes = fm.getBytes();
        OutputManager outManager = javaOutputManager;
        if(bytes!=null && outManager!=null){
            for(Map.Entry<String, byte[]> e:bytes.entrySet()){
                OutputStream os;
                try {
                    os = outManager.createOutputStream(e.getKey());
                    os.write(e.getValue());
                    os.close();
                } catch (IOException ex) {
                    handleIOException(ex);
                }
            }
        }
        getClassLoader().addClassLoader(javaCompiler);
        super.compile();
    }

    protected void handleJavaDiagnostic(Diagnostic<? extends JavaFileObject> d) {
        JavaFileObject source = d.getSource();
        if(source!=null){
            try {
                CharSequence code = source.getCharContent(true);
                System.err.print(code);
                System.err.println(source.getName() + ":" + d.getLineNumber());
            } catch (IOException ex) {
                Logger.getLogger(JointFileSystemCompiler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.err.println(d.getKind() + ":" + d.getMessage(null));
    }
    
    @Nullable
    protected JavaFileObject loadJavaSource(String className) throws IOException{
        //System.out.println("try loading java source:" + className);
        if(javaFiles.containsKey(className)){
            return (javaFiles.get(className));
        }
        for(File p:javaSourcePath){
            if(!p.isDirectory()) continue;
            File f = new File(p,ClassNameUtil.getRelativePathOfClass(className, "java"));
            if(FilePathUtil.existFile(f)){
                StringJavaSource source = StringJavaSource.loadFromFile(p, f);
                javaFiles.put(className, source);
                return source;
            }
        }
        JavaFileObject js = getJavaStub(className);
        if(js!=null) return js;
        CszzSource klSource = getSourceLoader().loadSource(className);
        if(klSource != null){
            addSource(klSource);
            //TODO here may generate stub repeatly
            generateJavaStub(javaStubManager);
            return getJavaStub(className);
        }
        return null;
    }
    
    @Nullable
    protected JavaFileObject getJavaStub(String className){
        byte[] data = javaStubManager.getBytes(className);
        if(data==null) return null;
        String code = new String(data);
        return new StringJavaSource(className, code);
    }

    public OutputManager getJavaOutputManager() {
        return javaOutputManager;
    }

    public void setJavaOutputManager(OutputManager javaOutputManager) {
        this.javaOutputManager = javaOutputManager;
    }

}
