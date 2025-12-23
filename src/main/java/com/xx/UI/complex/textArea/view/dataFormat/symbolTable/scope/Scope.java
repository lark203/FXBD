// Scope.java
package com.xx.UI.complex.textArea.view.dataFormat.symbolTable.scope;

import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.symbol.MethodSymbol;
import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.symbol.Symbol;
import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.type.Type;

import java.util.List;
import java.util.Map;

public interface Scope {
    String getScopeName();
    Scope getEnclosingScope();
    void define(Symbol sym);
    Symbol resolve(String name);
    Symbol resolveMember(String name);
    MethodSymbol resolveMethod(String name, List<Type> argTypes);
    Map<String, Symbol> getSymbols();
    
    // 新增方法：检查符号是否在当前作用域定义
    default boolean isDefinedLocally(String name) {
        return resolveMember(name) != null;
    }
}