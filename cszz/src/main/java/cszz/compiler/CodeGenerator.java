
package cszz.compiler;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import cszz.ast.ClassNode;
/**
 *
 *  
 */
public interface CodeGenerator {
    
    void generate(ClassNode classNode);
    
}
