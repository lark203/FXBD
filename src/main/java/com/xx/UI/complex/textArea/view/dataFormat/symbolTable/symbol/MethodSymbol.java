package com.xx.UI.complex.textArea.view.dataFormat.symbolTable.symbol;

import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.scope.Scope;
import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.type.Type;

import java.util.ArrayList;
import java.util.List;

public class MethodSymbol extends Symbol {
    private final List<Type> parameterTypes = new ArrayList<>();
    private final List<String> parameterNames = new ArrayList<>();
    private boolean isConstructor;
    private Type returnType;
    
    public MethodSymbol(String name, Type returnType, Scope scope) {
        super(name, scope);
        this.returnType = returnType;
    }
    
    public void addParameter(Type type, String name) {
        parameterTypes.add(type);
        parameterNames.add(name);
    }
    
    public List<Type> getParameterTypes() { return parameterTypes; }
    public List<String> getParameterNames() { return parameterNames; }
    public boolean isConstructor() { return isConstructor; }
    public void setConstructor(boolean constructor) { isConstructor = constructor; }
    public Type getReturnType() { return returnType; }
    public void setReturnType(Type returnType) { this.returnType = returnType; }
}