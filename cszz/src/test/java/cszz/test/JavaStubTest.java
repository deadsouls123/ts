package cszz.test;

import java.io.File;
import java.io.IOException;
import cszz.tool.FileSystemCompiler;
import org.junit.Test;

/**
 *
 *  
 */
public class JavaStubTest extends FileSystemCompiler{
    
    @Test
    public void test() throws IOException{
        addSourceDir(new File("examples"));
        //generateJavaStub(null);
    }

}
