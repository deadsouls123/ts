
package cszz.compiler;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import cszz.ast.ClassNode;
/**
 *
 * @author Kason Yang 
 */
public interface CodeGenerator {
    
    void generate(ClassNode classNode);
    
}
