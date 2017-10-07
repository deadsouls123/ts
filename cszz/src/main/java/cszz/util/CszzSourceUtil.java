
package cszz.util;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import cszz.compiler.CszzSource;
import org.apache.commons.io.FileUtils;
/**
 *
 *  
 */
public class CszzSourceUtil {
    public static CszzSource create(File dir,File sourceFile) throws IOException{
        String clsName = ClassNameUtil.getClassName(dir, sourceFile);
        CszzSource ks = new CszzSource(clsName,FileUtils.readFileToString(sourceFile),sourceFile.getName());
        return ks;        
    }
}
