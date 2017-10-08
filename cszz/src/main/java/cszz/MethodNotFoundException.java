package cszz;

import cszz.core.ObjectType;
/**
 *
 *  
 */
public class MethodNotFoundException extends Exception{

    public MethodNotFoundException(String message) {
        super(message);
    }

    public MethodNotFoundException(ObjectType type,String methodName) {
        super("method not found:" + type + "." + methodName);
    }
    
}
