package cszz.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;

/**
 *
 *  
 */
public class FilePathUtil {
	
	public static String LOCAL_PATH = System.getProperty("user.dir");
	
	public static String CLASS_PATH = LOCAL_PATH + File.separator + "a.class";
	
	public static String CMD_PATH = LOCAL_PATH + File.separator + "a.txt";

    public static boolean existFile(File srcFile) {
        String absPath = srcFile.getAbsolutePath();
        if (srcFile.exists()) {
            String canonicalPath;
            try {
                canonicalPath = srcFile.getCanonicalPath();
            } catch (IOException ex) {
                return false;
            }
            return FilenameUtils.equals(canonicalPath, absPath, true, IOCase.SENSITIVE);
        }
        return false;
    }
    
}
