package com.xx.UI.complex.textArea.view.dataFormat.symbolTable.type;

// 类型系统
public interface Type {
    String getName();
    boolean isAssignableFrom(Type other);
}