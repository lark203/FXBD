// PrimitiveType.java
package com.xx.UI.complex.textArea.view.dataFormat.symbolTable.type;

import java.util.HashMap;
import java.util.Map;

public class PrimitiveType implements Type {
    private final String name;
    private static final Map<String, Integer> TYPE_HIERARCHY = new HashMap<>();
    
    static {
        TYPE_HIERARCHY.put("byte", 1);
        TYPE_HIERARCHY.put("short", 2);
        TYPE_HIERARCHY.put("char", 3);
        TYPE_HIERARCHY.put("int", 4);
        TYPE_HIERARCHY.put("long", 5);
        TYPE_HIERARCHY.put("float", 6);
        TYPE_HIERARCHY.put("double", 7);
    }
    
    public PrimitiveType(String name) {
        this.name = name;
    }
    
    @Override public String getName() { return name; }
    
    @Override
    public boolean isAssignableFrom(Type other) {
        if (!(other instanceof PrimitiveType)) return false;
        
        String otherName = ((PrimitiveType) other).name;
        
        // 相同类型
        if (name.equals(otherName)) return true;
        
        // 检查类型提升
        Integer thisRank = TYPE_HIERARCHY.get(name);
        Integer otherRank = TYPE_HIERARCHY.get(otherName);
        
        if (thisRank == null || otherRank == null) return false;
        
        return otherRank <= thisRank;
    }
}