package cszz.core;

import cszz.ast.MethodNode;

/**
 *
 * @author Kason Yang
 */
public class ConstructorDescriptor extends ExecutableDescriptor{

    public ConstructorDescriptor(MethodNode method,ParameterDescriptor[] parameterDescriptors,Type[] exceptionTypes) {
        super(method, parameterDescriptors,Types.VOID_TYPE,exceptionTypes);
    }

}
