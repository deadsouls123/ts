
package cszz.compiler;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Kason Yang 
 */
public class CszzSource {
    
    private String className;
    
    private String text;
    
    private String fileName;

    public CszzSource(String className, String text,String fileName) {
        this.className = className;
        this.text = text;
        this.fileName = fileName;
    }

    public String getClassName() {
        return className;
    }

    public String getText() {
        return text;
    }

    public CszzSource(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

}
