package com.xx.UI.complex.textArea.view.dataFormat.symbolTable.scope;

// 辅助类
public class Import {
    private final String fullName;
    private final boolean isStatic;
    private final boolean isWildcard;
    
    public Import(String fullName, boolean isStatic, boolean isWildcard) {
        this.fullName = fullName;
        this.isStatic = isStatic;
        this.isWildcard = isWildcard;
    }
    
    public String getSimpleName() {
        if (isWildcard) return fullName.substring(0, fullName.lastIndexOf('.'));
        return fullName.substring(fullName.lastIndexOf('.') + 1);
    }
    
    public String getFullName() { return fullName; }
    public boolean isStatic() { return isStatic; }
    public boolean isWildcard() { return isWildcard; }
}