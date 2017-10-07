
package cszz;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import cszz.ast.ClassNode;
import cszz.compiler.codegen.Ast2Class;
import cszz.compiler.CodeGenerator;
import cszz.compiler.CompilationUnit;
import cszz.compiler.CszzCompiler;
import cszz.compiler.CszzSource;
import cszz.compiler.SourceLoader;
import cszz.tool.FileSystemSourceLoader;
import cszz.tool.MemoryOutputManager;
import cszz.tool.OutputManager;
import org.apache.commons.io.FileUtils;
/**
 *
 * 
 */
public class CszzClassLoader extends URLClassLoader implements CodeGenerator{

    private final CszzCompiler compiler;
    
    private final HashMap<String,Class> loadedClasses = new HashMap<>();
    private final FileSystemSourceLoader sourceLoader;
    
    public CszzClassLoader() {
        this(new File[0]);
    }

    public CszzClassLoader(File[] sourceDir) {
        super(new URL[0]);
        sourceLoader = new FileSystemSourceLoader(sourceDir,new String[]{"kl","cszz"});
        CodeGenerator cg = this;
        compiler = new CszzCompiler(){
            @Override
            public SourceLoader getSourceLoader() {
                return sourceLoader;
            }

            @Override
            public CodeGenerator createCodeGenerator(CompilationUnit compilationUnit) {
                return cg;
            }
            
        };
    }
    
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clazz = loadedClasses.get(name);
        if(clazz!=null) return clazz;
        CszzSource src = sourceLoader.loadSource(name);
        if(src!=null){
            compiler.addSource(src);
            compiler.compile();
        }
        clazz = loadedClasses.get(name);
        if(clazz!=null) return clazz;
        return super.findClass(name);
    }

    @Override
    public void generate(ClassNode classNode) {
        final MemoryOutputManager outputManager = new MemoryOutputManager();
        Ast2Class ast2Class = new Ast2Class(outputManager);
        ast2Class.generate(classNode);
        String[] names = outputManager.getClassNames();
        for(String name:names){
            byte[] bs = outputManager.getBytes(name);
            if(bs!=null){
                Class<?> clazz = defineClass(name, bs,0,bs.length);
                loadedClasses.put(name, clazz);
            }    
        }
    }

    public void addClassPath(File path) {
        try {
            super.addURL(path.toURI().toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CszzClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        sourceLoader.addSourceDir(path);
    }
    
    public Class parseSource(String className,String code,String fileName){
        compiler.addSource(className, code, fileName);
        compiler.compile();
        return loadedClasses.get(className);
    }
    
    public Class parseFile(String className,File file) throws IOException{
        String code = FileUtils.readFileToString(file);
        return parseSource(className, code, file.getName());
    }
    
}
