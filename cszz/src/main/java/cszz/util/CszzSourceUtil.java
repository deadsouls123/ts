package cszz.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import cszz.compiler.CszzSource;
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
