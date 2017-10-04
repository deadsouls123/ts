package cszz.ast;
import java.util.*;
import javax.annotation.Nullable;
import cszz.core.*;
public class ParameterExpr extends ExprNode{
    
    protected ParameterNode parameter;
    
    protected Type overrideType = null;
    
    public ParameterExpr(ParameterNode parameter){
        this.parameter = parameter;
    }

    public ParameterExpr(ParameterNode parameter,@Nullable Type overrideType) {
        this.parameter = parameter;
        this.overrideType = overrideType;
    }

    @Override
    public Type getType() {
        return overrideType !=null ? overrideType : parameter.getType();
    }

    /**
     * @return the parameter
     */
    public ParameterNode getParameter() {
        return parameter;
    }
    
    public void removeOverrideType(){
        overrideType = null;
    }

}
