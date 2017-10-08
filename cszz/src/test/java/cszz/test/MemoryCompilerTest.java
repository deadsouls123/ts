package cszz.test;

import java.io.File;

import org.junit.Test;

import cszz.java.MemoryCompiler;
import cszz.java.MemoryFileManager;
import junit.framework.Assert;

/**
 *
 *  
 */
public class MemoryCompilerTest {
    
    @Test
    public void testSourcePath() throws ClassNotFoundException{
        MemoryCompiler cp = new MemoryCompiler();
        cp.addSourcePath(new File("TestScript/java"));
        Class<?> clazz = cp.loadClass("Helloworld");
        Assert.assertNotNull(clazz);
    }
    
    @Test
    public void test() throws ClassNotFoundException {
        MemoryCompiler cp = new MemoryCompiler();
        cp.addSourceFromString("Helloworld", "public class Helloworld{}");
        cp.compile();
        MemoryFileManager fm = cp.getFileManager();
        Assert.assertNotNull(fm.getBytes());
        Class clazz = cp.loadClass("Helloworld");
        Assert.assertNotNull(clazz);
    }

}
