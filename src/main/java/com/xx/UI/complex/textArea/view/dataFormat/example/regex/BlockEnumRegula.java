package com.xx.UI.complex.textArea.view.dataFormat.example.regex;

import com.xx.UI.complex.textArea.view.dataFormat.analyse.Analyse;

public enum BlockEnumRegula implements Analyse.BDTextEnum<BlockEnumRegula>  {
    UNDEFINED(-2),         // 未定义类型
    STRING(0),             // 字符串
    COMMENT(1),            // 单行注释
    DOC_COMMENT(2),        // 文档注释（Javadoc）
    KEYWORD(3),            // Java关键字
    DATATYPE(4),           // 数据类型（基本类型和包装类）
    ANNOTATION(5),         // 注解
    CLASS_DEF(6),          // 类/接口/枚举定义
    METHOD_DEF(7),         // 方法定义
    METHOD_CALL(8),        // 方法调用
    CONSTANT(9),           // 常量（数值、布尔值、null）
    OPERATOR(10),          // 运算符
    SPECIAL_SYNTAX(11),    // 特殊语法（泛型、lambda等）
    PACKAGE(12),           // 包声明
    IMPORT(13),            // 导入声明
    FIELD(14),             // 字段/变量
    PARAMETER(15),         // 方法参数
    EXCEPTION(16),         // 异常类型
    GENERIC(17),           // 泛型类型参数
    STATIC_MEMBER(18),     // 静态成员（静态导入）
    MODIFIER(19),          // 访问修饰符
    CONTROL_FLOW(20),      // 控制流关键字（if/for/while等）
    ENUM_CONSTANT(21),     // 枚举常量
    RECORD_COMPONENT(22),  // Record组件
    LAMBDA(23),            // Lambda表达式
    ANONYMOUS_CLASS(24),   // 匿名类
    TYPE_CAST(25),         // 类型转换
    ASSERTION(26),         // 断言
    SYNCHRONIZED(27),      // 同步块
    VOLATILE(28),          // volatile变量
    TRANSIENT(29),         // transient字段
    NATIVE(30),            // native方法
    THREAD(31),            // 线程相关关键字
    DEPRECATED(32),        // @Deprecated标记
    OVERRIDE(33),          // @Override标记
    FUNCTIONAL_INTERFACE(34), // 函数式接口
    MODULE(35),            // 模块系统相关
    RECORD(36),            // Record类型
    SEALED(37),            // Sealed类
    NON_SEALED(38),        // Non-sealed类
    PERMITS(39),           // Permits关键字
    VAR(40),               // var类型推断
    YIELD(41);             // yield关键字（switch表达式）

    private final int value;

    BlockEnumRegula(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * 根据整数值查找对应的枚举常量
     */
    public static BlockEnumRegula fromValue(int value) {
        for (BlockEnumRegula type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的Token类型值: " + value);
    }

    /**
     * 根据名称查找对应的枚举常量（不区分大小写）
     */
    public static BlockEnumRegula fromName(String name) {
        for (BlockEnumRegula type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的Token类型名称: " + name);
    }

    @Override
    public BlockEnumRegula undefinedType() {
        return UNDEFINED;
    }

    @Override
    public String toString() {
        return name() + "(" + value + ")";
    }
}