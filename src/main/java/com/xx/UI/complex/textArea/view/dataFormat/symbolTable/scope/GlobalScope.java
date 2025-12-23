// GlobalScope.java
package com.xx.UI.complex.textArea.view.dataFormat.symbolTable.scope;

import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.symbol.ClassSymbol;
import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.symbol.Symbol;
import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.type.PrimitiveType;

import java.util.*;

public class GlobalScope extends BaseScope {
    private final Map<String, ClassSymbol> classes = new HashMap<>();
    private final Map<String, Import> imports = new HashMap<>();
    private String packageName = "";

    public GlobalScope() {
        super("global", null);
        definePrimitiveTypes();
    }

    private void definePrimitiveTypes() {
        String[] primitives = {"int", "byte", "short", "long", "float",
                              "double", "char", "boolean", "void"};
        for (String type : primitives) {
            PrimitiveType pt = new PrimitiveType(type);
            ClassSymbol cs = new ClassSymbol(type, pt, this);
            classes.put(type, cs);
            define(cs);
        }
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void addImport(Import imp) {
        if (imp.isWildcard()) {
            // 通配符导入处理
            String packagePath = imp.getFullName().substring(0, imp.getFullName().length() - 2);
            imports.put(packagePath + ".*", imp);
        } else {
            // 精确导入处理
            imports.put(imp.getSimpleName(), imp);
        }
    }

    public ClassSymbol resolveClass(String className) {
        // 1. 检查完全限定名
        if (classes.containsKey(className)) {
            return classes.get(className);
        }

        // 2. 检查当前包中的类
        if (!packageName.isEmpty()) {
            String fqn = packageName + "." + className;
            if (classes.containsKey(fqn)) {
                return classes.get(fqn);
            }
        }

        // 3. 检查导入的类
        Import imp = imports.get(className);
        if (imp != null && !imp.isWildcard()) {
            return classes.get(imp.getFullName());
        }

        // 4. 检查通配符导入
        for (Import wildcardImp : imports.values()) {
            if (wildcardImp.isWildcard()) {
                String fqn = wildcardImp.getFullName().substring(0, wildcardImp.getFullName().length() - 1) + className;
                if (classes.containsKey(fqn)) {
                    return classes.get(fqn);
                }
            }
        }

        return null;
    }

    @Override
    public Symbol resolve(String name) {
        Symbol s = super.resolve(name);
        if (s == null) {
            s = resolveClass(name);
        }
        return s;
    }

    // 添加类到全局作用域
    public void addClass(String className, ClassSymbol classSymbol) {
        classes.put(className, classSymbol);
        define(classSymbol);
    }
}