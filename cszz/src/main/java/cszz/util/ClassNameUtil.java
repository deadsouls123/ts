
package cszz.util;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import javax.annotation.Nullable;
import org.apache.commons.io.FilenameUtils;
/**
 *
 * @author Kason Yang 
 */
public class ClassNameUtil {
    
    public static String getRelativePathOfClass(String className,@Nullable String extention){
        String name = className.replace(".", "/");
        if(extention!=null){
            name += "." + extention;
        }
        return name;
    }
    
    public static String getClassName(File dir,File file){
        String dirPath = FilenameUtils.normalizeNoEndSeparator(dir.getAbsolutePath());
        String fname =FilenameUtils.normalizeNoEndSeparator(file.getAbsolutePath());
        String ext = FilenameUtils.getExtension(fname);
        String clsName = fname.substring(dirPath.length() + 1, fname.length() - ext.length()-1).replace(File.separator, ".");
        return clsName;
    }

}
