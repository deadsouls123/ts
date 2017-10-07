package cszz.core;

import cszz.ast.MethodNode;

/**
 *
 * 
 */
public class ConstructorDescriptor extends ExecutableDescriptor{

    public ConstructorDescriptor(MethodNode method,ParameterDescriptor[] parameterDescriptors,Type[] exceptionTypes) {
        super(method, parameterDescriptors,Types.VOID_TYPE,exceptionTypes);
    }

}
