// ClassSymbol.java
package com.xx.UI.complex.textArea.view.dataFormat.symbolTable.symbol;

import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.scope.Scope;
import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.type.ClassType;
import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.type.Type;

import java.util.ArrayList;
import java.util.List;

public class ClassSymbol extends Symbol {
    private String superClassName;
    private final List<String> interfaceNames = new ArrayList<>();
    private boolean isInterface;

    public ClassSymbol(String name, Scope scope) {
        // 修正：传递作用域给ClassType构造函数
        this(name, new ClassType(name, scope), scope);
    }

    public ClassSymbol(String name, Type type, Scope scope) {
        super(name, type, scope);
    }
    
    public void setSuperClass(String superClassName) {
        this.superClassName = superClassName;
    }
    
    public void addInterface(String interfaceName) {
        interfaceNames.add(interfaceName);
    }
    
    public String getSuperClassName() { return superClassName; }
    public List<String> getInterfaceNames() { return interfaceNames; }
    public boolean isInterface() { return isInterface; }
    public void setInterface(boolean anInterface) { isInterface = anInterface; }
}