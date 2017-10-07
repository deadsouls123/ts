
package cszz.ast;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import javax.annotation.Nullable;
import cszz.core.Type;
/**
 *
 *  
 */
public class LocalVarNode extends VarObject{
    
    public LocalVarNode(Type type,@Nullable String name){
        this(type,name,0);
    }

    public LocalVarNode(Type type, String name,int modifier) {
        super(modifier, type, name);
    }

}
