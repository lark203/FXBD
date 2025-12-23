package com.xx.UI.complex.textArea.view.dataFormat.example.json.imp;

import com.xx.UI.complex.textArea.view.dataFormat.analyse.Analyse;

public enum BlockEnumJson implements Analyse.BDTextEnum<BlockEnumJson> {
    UNDEFINED(-1),
    T__0(1),      // {
    T__1(2),      // ,
    T__2(3),      // }
    T__3(4),      // :
    T__4(5),      // [
    T__5(6),      // ]
    T__6(7),      // true
    T__7(8),      // false
    T__8(9),      // null
    STRING(10),   // 字符串值
    KEY(13),      // JSON 键名 - 新增
    NUMBER(11),   // 数字
    WS(12);       // 空白字符

    private final int value;

    BlockEnumJson(int value) {
        this.value = value;
    }

    /**
     * 根据整数值查找对应的枚举常量
     */
    public static BlockEnumJson fromValue(int value) {
        for (BlockEnumJson type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        return UNDEFINED;
    }

    /**
     * 根据名称查找对应的枚举常量（不区分大小写）
     */
    public static BlockEnumJson fromName(String name) {
        for (BlockEnumJson type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return UNDEFINED;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name() + "(" + value + ")";
    }

    @Override
    public BlockEnumJson undefinedType() {
        return UNDEFINED;
    }
}