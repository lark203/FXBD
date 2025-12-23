// ClassScope.java
package com.xx.UI.complex.textArea.view.dataFormat.symbolTable.scope;

import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.symbol.ClassSymbol;
import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.symbol.MethodSymbol;
import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.symbol.Symbol;
import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.type.Type;

import java.util.List;

public class ClassScope extends BaseScope {
    private final ClassSymbol classSymbol;

    public ClassScope(ClassSymbol classSymbol, Scope enclosingScope) {
        super("Class:" + classSymbol.getName(), enclosingScope);
        this.classSymbol = classSymbol;
    }

    @Override
    public Symbol resolveMember(String name) {
        Symbol s = super.resolveMember(name);
        if (s != null) return s;

        // 在父类中查找
        if (classSymbol.getSuperClassName() != null) {
            ClassSymbol superClass = resolveSuperClass();
            if (superClass != null) {
                Scope superScope = superClass.getScope();
                s = superScope.resolveMember(name);
            }
        }
        return s;
    }

    @Override
    public MethodSymbol resolveMethod(String name, List<Type> argTypes) {
        // 在当前类查找
        MethodSymbol method = super.resolveMethod(name, argTypes);
        if (method != null) return method;

        // 在父类中查找
        if (classSymbol.getSuperClassName() != null) {
            ClassSymbol superClass = resolveSuperClass();
            if (superClass != null) {
                Scope superScope = superClass.getScope();
                return superScope.resolveMethod(name, argTypes);
            }
        }

        // 在接口中查找
        for (String interfaceName : classSymbol.getInterfaceNames()) {
            ClassSymbol interfaceSymbol = resolveInterface(interfaceName);
            if (interfaceSymbol != null) {
                MethodSymbol interfaceMethod = interfaceSymbol.getScope().resolveMethod(name, argTypes);
                if (interfaceMethod != null) return interfaceMethod;
            }
        }

        return null;
    }

    private ClassSymbol resolveSuperClass() {
        Symbol sym = getEnclosingScope().resolve(classSymbol.getSuperClassName());
        return (sym instanceof ClassSymbol) ? (ClassSymbol) sym : null;
    }

    private ClassSymbol resolveInterface(String interfaceName) {
        Symbol sym = getEnclosingScope().resolve(interfaceName);
        return (sym instanceof ClassSymbol) ? (ClassSymbol) sym : null;
    }
}