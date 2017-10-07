
package cszz.ast;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import cszz.core.Type;
import cszz.core.Types;
/**
 *
 *  
 */
public class LogicExpr extends BinaryExpr{

    public LogicExpr(ExprNode expr1, ExprNode expr2, String operation) {
        super(expr1, expr2, operation);
    }

    @Override
    public Type getType() {
        return Types.BOOLEAN_TYPE;
    }

}
