package cszz.ast;

import javax.annotation.Nullable;

import cszz.core.Type;

public class VarExpr extends AssignableExpr{
    
    protected final LocalVarNode var;
    
    @Nullable
    protected Type overrideType;
    
    public VarExpr(LocalVarNode var){
        this.var = var;
    }

    public VarExpr(LocalVarNode var,@Nullable Type overrideType) {
        this.var = var;
        this.overrideType = overrideType;
    }
    
    @Override
    public Type getType() {
        return overrideType !=null ? overrideType : var.getType();
    }

    /**
     * @return the var
     */
    public LocalVarNode getVar() {
        return var;
    }

    @Override
    public String toString() {
        return var.getName();
    }
    
    public void removeOverrideType(){
        overrideType = null;
    }
    
    
    
}
