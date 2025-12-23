package com.xx.UI.complex.textArea.view.dataFormat.symbolTable.symbol;

import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.scope.Scope;
import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.type.Type;

public class VariableSymbol extends Symbol {
    private boolean isField;
    private boolean isParameter;
    
    public VariableSymbol(String name, Type type, Scope scope) {
        super(name, type,scope);
    }
    
    public boolean isField() { return isField; }
    public void setField(boolean field) { isField = field; }
    public boolean isParameter() { return isParameter; }
    public void setParameter(boolean parameter) { isParameter = parameter; }
}
