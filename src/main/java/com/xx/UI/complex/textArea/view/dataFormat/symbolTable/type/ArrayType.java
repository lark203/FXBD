// ArrayType.java
package com.xx.UI.complex.textArea.view.dataFormat.symbolTable.type;

public class ArrayType implements Type {
    private final Type componentType;
    private final int dimensions;

    public ArrayType(Type componentType, int dimensions) {
        this.componentType = componentType;
        this.dimensions = dimensions;
    }

    @Override public String getName() {
        return componentType.getName() + "[]".repeat(dimensions);
    }

    @Override
    public boolean isAssignableFrom(Type other) {
        if (!(other instanceof ArrayType otherArray)) return false;

        // 维度必须相同
        if (dimensions != otherArray.dimensions) return false;

        // 检查组件类型兼容性
        return componentType.isAssignableFrom(otherArray.componentType);
    }
}