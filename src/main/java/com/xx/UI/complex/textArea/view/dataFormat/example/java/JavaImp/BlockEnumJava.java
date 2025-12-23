package com.xx.UI.complex.textArea.view.dataFormat.example.java.JavaImp;

import com.xx.UI.complex.textArea.view.dataFormat.analyse.Analyse;

public enum BlockEnumJava implements Analyse.BDTextEnum<BlockEnumJava> {
    UNDEFINED(-2),
    EOF(-1),
    EXPORTS(1),
    MODULE(2),
    NONSEALED(3),
    OACA(4),
    OPEN(5),
    OPENS(6),
    PERMITS(7),
    PROVIDES(8),
    RECORD(9),
    REQUIRES(10),
    SEALED(11),
    TO(12),
    TRANSITIVE(13),
    USES(14),
    VAR(15),
    WITH(16),
    YIELD(17),
    ABSTRACT(18),
    ASSERT(19),
    BOOLEAN(20),
    BREAK(21),
    BYTE(22),
    CASE(23),
    CATCH(24),
    CHAR(25),
    CLASS(26),
    CONST(27),
    CONTINUE(28),
    DEFAULT(29),
    DO(30),
    DOUBLE(31),
    ELSE(32),
    ENUM(33),
    EXTENDS(34),
    FINAL(35),
    FINALLY(36),
    FLOAT(37),
    FOR(38),
    IF(39),
    GOTO(40),
    IMPLEMENTS(41),
    IMPORT(42),
    INSTANCEOF(43),
    INT(44),
    INTERFACE(45),
    LONG(46),
    NATIVE(47),
    NEW(48),
    PACKAGE(49),
    PRIVATE(50),
    PROTECTED(51),
    PUBLIC(52),
    RETURN(53),
    SHORT(54),
    STATIC(55),
    STRICTFP(56),
    SUPER(57),
    SWITCH(58),
    SYNCHRONIZED(59),
    THIS(60),
    THROW(61),
    THROWS(62),
    TRANSIENT(63),
    TRY(64),
    VOID(65),
    VOLATILE(66),
    WHILE(67),
    UNDER_SCORE(68),
    IntegerLiteral(69),
    FloatingPointLiteral(70),
    BooleanLiteral(71),
    CharacterLiteral(72),
    StringLiteral(73),
    TextBlock(74),
    NullLiteral(75),
    LPAREN(76),
    RPAREN(77),
    LBRACE(78),
    RBRACE(79),
    LBRACK(80),
    RBRACK(81),
    SEMI(82),
    COMMA(83),
    DOT(84),
    ELLIPSIS(85),
    AT(86),
    COLONCOLON(87),
    ASSIGN(88),
    GT(89),
    LT(90),
    BANG(91),
    TILDE(92),
    QUESTION(93),
    COLON(94),
    ARROW(95),
    EQUAL(96),
    LE(97),
    GE(98),
    NOTEQUAL(99),
    AND(100),
    OR(101),
    INC(102),
    DEC(103),
    ADD(104),
    SUB(105),
    MUL(106),
    DIV(107),
    BITAND(108),
    BITOR(109),
    CARET(110),
    MOD(111),
    ADD_ASSIGN(112),
    SUB_ASSIGN(113),
    MUL_ASSIGN(114),
    DIV_ASSIGN(115),
    AND_ASSIGN(116),
    OR_ASSIGN(117),
    XOR_ASSIGN(118),
    MOD_ASSIGN(119),
    LSHIFT_ASSIGN(120),
    RSHIFT_ASSIGN(121),
    URSHIFT_ASSIGN(122),
    Identifier(123),
    WS(124),
    COMMENT(125),
    LINE_COMMENT(126),
    METHOD_NAME(127),
    FIELD_NAME(128),
    ERROR(129);

    private final int value;

    BlockEnumJava(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * 根据整数值查找对应的枚举常量
     */
    public static BlockEnumJava fromValue(int value) {
        for (BlockEnumJava type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的Token类型值: " + value);
    }

    /**
     * 根据名称查找对应的枚举常量（不区分大小写）
     */
    public static BlockEnumJava fromName(String name) {
        for (BlockEnumJava type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的Token类型名称: " + name);
    }

    @Override
    public String toString() {
        return name() + "(" + value + ")";
    }

    @Override
    public BlockEnumJava undefinedType() {
        return fromValue(-1);
    }
}