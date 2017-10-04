
package cszz.tool;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import cszz.compiler.CszzSource;
import cszz.compiler.SourceLoader;
import cszz.util.FilePathUtil;
import cszz.util.CszzSourceUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
/**
 *  The class load source from file system.
 * @author Kason Yang 
 */
public class FileSystemSourceLoader implements SourceLoader {

    private final List<File> srcDirs = new ArrayList<>();
    
    private final List<String> extentions = new ArrayList<>();
    
    public FileSystemSourceLoader(File[] srcDir,String[] extentions) {
        this.srcDirs.addAll(Arrays.asList(srcDir));
        this.extentions.addAll(Arrays.asList(extentions));
    }
    
    public void addSourceDir(File dir){
        srcDirs.add(dir);
    }
    
    public void addExtention(String ext){
        extentions.add(ext);
    }
    
    @Override
    public CszzSource loadSource(String className) {
        for(String e:extentions){
            String fn = className.replace(".", "/") + "." + e;
            for(File s:srcDirs){
                File srcFile = new File(s,fn);
                if(FilePathUtil.existFile(srcFile)){
                    try {
                        return CszzSourceUtil.create(s,srcFile);
                    } catch (IOException ex) {
                        Logger.getLogger(FileSystemSourceLoader.class.getName()).log(Level.SEVERE, null, ex);
                        return null;
                    }
                }
            }
        }
        return null;
    }

}
