// BaseScope.java
package com.xx.UI.complex.textArea.view.dataFormat.symbolTable.scope;

import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.symbol.MethodSymbol;
import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.symbol.Symbol;
import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.type.Type;

import java.util.*;

public abstract class BaseScope implements Scope {
    protected final Scope enclosingScope;
    protected final Map<String, Symbol> symbols = new LinkedHashMap<>();
    protected final String scopeName;

    public BaseScope(String scopeName, Scope enclosingScope) {
        this.scopeName = scopeName;
        this.enclosingScope = enclosingScope;
    }

    @Override public String getScopeName() { return scopeName; }
    @Override public Scope getEnclosingScope() { return enclosingScope; }

    @Override
    public void define(Symbol sym) {
        symbols.put(sym.getName(), sym);
        sym.setScope(this);
    }

    @Override
    public Symbol resolve(String name) {
        Symbol s = symbols.get(name);
        if (s != null) return s;
        if (enclosingScope != null) return enclosingScope.resolve(name);
        return null;
    }

    @Override
    public Symbol resolveMember(String name) {
        return symbols.get(name);
    }

    @Override
    public MethodSymbol resolveMethod(String name, List<Type> argTypes) {
        // 在当前作用域查找匹配方法
        Symbol sym = symbols.get(name);
        if (sym instanceof MethodSymbol) {
            MethodSymbol method = (MethodSymbol) sym;
            if (isMethodCompatible(method, argTypes)) {
                return method;
            }
        }
        return null;
    }

    protected boolean isMethodCompatible(MethodSymbol method, List<Type> argTypes) {
        List<Type> paramTypes = method.getParameterTypes();
        if (paramTypes.size() != argTypes.size()) return false;
        
        for (int i = 0; i < paramTypes.size(); i++) {
            if (!paramTypes.get(i).isAssignableFrom(argTypes.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Map<String, Symbol> getSymbols() {
        return Collections.unmodifiableMap(symbols);
    }
}