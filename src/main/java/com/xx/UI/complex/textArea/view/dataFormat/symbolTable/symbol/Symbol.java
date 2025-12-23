// Symbol.java
package com.xx.UI.complex.textArea.view.dataFormat.symbolTable.symbol;

import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.scope.Scope;
import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.type.Type;

import java.util.ArrayList;
import java.util.List;

public abstract class Symbol {
    protected final String name;
    protected Type type;
    protected final List<Modifier> modifiers = new ArrayList<>();
    protected Scope scope;

    public Symbol(String name, Scope scope) {
        this.name = name;
        this.scope = scope;
    }
    
    public Symbol(String name, Type type, Scope scope) {
        this.name = name;
        this.type = type;
        this.scope = scope;
    }
    
    public String getName() { return name; }
    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
    public List<Modifier> getModifiers() { return modifiers; }
    
    public void addModifier(Modifier modifier) {
        if (!modifiers.contains(modifier)) {
            modifiers.add(modifier);
        }
    }
    
    public boolean isPublic() {
        return modifiers.contains(Modifier.PUBLIC);
    }
    
    public boolean isPrivate() {
        return modifiers.contains(Modifier.PRIVATE);
    }
    
    public boolean isProtected() {
        return modifiers.contains(Modifier.PROTECTED);
    }
    
    public boolean isStatic() {
        return modifiers.contains(Modifier.STATIC);
    }
    
    public boolean isFinal() {
        return modifiers.contains(Modifier.FINAL);
    }
    
    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
    
    // 新增方法：检查符号是否具有指定修饰符
    public boolean hasModifier(Modifier modifier) {
        return modifiers.contains(modifier);
    }
}