package cszz.tool;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 *  
 */
public interface OutputManager {
    
    OutputStream createOutputStream(String className) throws IOException;

}
