// ClassType.java
package com.xx.UI.complex.textArea.view.dataFormat.symbolTable.type;

import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.scope.Scope;
import com.xx.UI.complex.textArea.view.dataFormat.symbolTable.symbol.ClassSymbol;

import java.util.Collections;
import java.util.List;

public class ClassType implements Type {
    private final String className;
    private final List<Type> typeArguments;
    private final Scope enclosingScope;  // 新增字段：保存作用域引用

    public ClassType(String className, Scope enclosingScope) {
        this(className, Collections.emptyList(), enclosingScope);
    }

    public ClassType(String className, List<Type> typeArguments, Scope enclosingScope) {
        this.className = className;
        this.typeArguments = typeArguments;
        this.enclosingScope = enclosingScope;
    }

    @Override public String getName() { return className; }
    public List<Type> getTypeArguments() { return typeArguments; }

    @Override
    public boolean isAssignableFrom(Type other) {
        if (other == null) return false;
        if (this == other) return true;

        // 处理Object是所有类的父类
        if ("java.lang.Object".equals(className)) {
            return other instanceof ClassType;
        }

        // 处理相同类名
        if (other instanceof ClassType otherType) {
            if (className.equals(otherType.className)) return true;
        }

        // 获取当前类的符号
        if (enclosingScope == null) return false;
        ClassSymbol thisClass = (ClassSymbol) enclosingScope.resolve(className);
        if (thisClass == null) return false;

        // 检查父类
        String superClassName = thisClass.getSuperClassName();
        if (superClassName != null) {
            Type superType = new ClassType(superClassName, enclosingScope);
            if (superType.isAssignableFrom(other)) {
                return true;
            }
        }

        // 检查接口
        for (String interfaceName : thisClass.getInterfaceNames()) {
            Type interfaceType = new ClassType(interfaceName, enclosingScope);
            if (interfaceType.isAssignableFrom(other)) {
                return true;
            }
        }

        return false;
    }
}