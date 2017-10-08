package cszz.tool;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

import cszz.util.ClassNameUtil;

/**
 *
 *  
 */
public class FileSystemOutputManager implements OutputManager{
    
    File outputDir;
    
    String extention;

    public FileSystemOutputManager(File outputDir, String extention) {
        this.outputDir = outputDir;
        this.extention = extention;
    }

    @Override
    public OutputStream createOutputStream(String className) throws IOException{
        String relativePath = ClassNameUtil.getRelativePathOfClass(className, extention);
        File outputFile = new File(outputDir,relativePath);
        return FileUtils.openOutputStream(outputFile, false);
    }

}
