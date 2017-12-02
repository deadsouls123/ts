package cszz.runtime.dynamic;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 *
 *  
 */
public class MethodAmbiguousException extends Exception{

	private static final long serialVersionUID = 2137788015072355748L;

	protected Method[] ambiguousMethods;

    public MethodAmbiguousException(Method[] ambiguousMethods) {
        this.ambiguousMethods = ambiguousMethods;
    }

    @Override
    public String toString() {
        return "method is ambiguous:" + Arrays.toString(ambiguousMethods);
    }
}
