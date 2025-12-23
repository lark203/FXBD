package com.xx.UI.complex.textArea.view.dataFormat.example.regex;

import com.xx.UI.complex.textArea.view.dataFormat.analyse.RegexAnalyse;

import java.util.Arrays;

public class BDRegexAnalyse extends RegexAnalyse<BlockEnumRegula> {

    public BDRegexAnalyse(String text) {
        super(text, BlockEnumRegula.UNDEFINED);
        init();
        setTextAsync(text, () -> {});
    }

    private void init() {


        // ===== 3. 关键字 =====
        String[] javaKeywords = {
            "abstract", "assert", "boolean", "break", "byte", "case", "catch",
            "char", "class", "const", "continue", "default", "do", "double",
            "else", "enum", "extends", "final", "finally", "float", "for",
            "goto", "if", "implements", "import", "instanceof", "int",
            "interface", "long", "native", "new", "package", "private",
            "protected", "public", "return", "short", "static", "strictfp",
            "super", "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while", "record", "sealed",
            "non-sealed", "permits", "var", "yield"
        };
        addKeywords(BlockEnumRegula.KEYWORD, Arrays.asList(javaKeywords), false, true);

        // ===== 4. 数据类型 =====
        String[] javaTypes = {
            "byte", "short", "int", "long", "float", "double", "char",
            "boolean", "String", "Integer", "Double", "Float", "Boolean",
            "Character", "Long", "Short", "Byte", "Object", "List", "Set",
            "Map", "ArrayList", "HashMap", "HashSet"
        };
        addKeywords(BlockEnumRegula.DATATYPE, Arrays.asList(javaTypes), true, true);



        // ===== 6. 类和方法 =====
        // 类定义 (public class MyClass)
        addRegexRule(BlockEnumRegula.CLASS_DEF, "\\b(class|interface|enum|record)\\s+([A-Z][\\w$]*)", true);
        // 方法定义
        addRegexRule(BlockEnumRegula.METHOD_DEF,
                   "\\b(?:public|protected|private|static|final|synchronized|native)\\s+" +  // 修饰符
                   "[\\w.<>,\\s]+?\\s+" +  // 返回类型
                   "([\\w$]+)\\s*\\(",  // 方法名
                   true);
        // 方法调用
        addRegexRule(BlockEnumRegula.METHOD_CALL, "\\b([a-z]\\w*)\\s*\\(", false);

        // ===== 7. 常量 =====
        // 数值常量
        addRegexRule(BlockEnumRegula.CONSTANT,
                   "\\b(?:\\d+\\.\\d*|\\d*\\.\\d+)|" +  // 浮点数
                   "\\b(?:0x[\\da-fA-F]+|0b[01]+|\\d+)|" +  // 整数（十进制、十六进制、二进制）
                   "\\b(true|false|null)\\b",  // 布尔值和null
                   false);
        // 常量（全大写命名）
        addRegexRule(BlockEnumRegula.CONSTANT, "\\b[A-Z][A-Z0-9_]+\\b", true);

        // ===== 8. 运算符 =====
        String[] operators = {
            "\\+", "-", "\\*", "/", "%", "=", "==", "!=", ">", "<", ">=", "<=",
            "\\|\\|", "&&", "!", "\\?", ":", "\\+\\+", "--", "\\+=", "-=", "\\*=",
            "/=", "%=", "&", "\\|", "\\^", "~", "<<", ">>", ">>>", "instanceof",
            "->", "::"
        };
        addRegexRule(BlockEnumRegula.OPERATOR, String.join("|", operators), true);

        // ===== 9. 特殊语法 =====
        // try-with-resources
        addRegexRule(BlockEnumRegula.SPECIAL_SYNTAX, "try\\s*\\(", true);
        // lambda 表达式
        addRegexRule(BlockEnumRegula.SPECIAL_SYNTAX, "->", true);
        // 泛型
        addRegexRule(BlockEnumRegula.SPECIAL_SYNTAX, "<[\\w\\s,?&]+>", true);

        // ===== 10. 包和导入 =====
        addRegexRule(BlockEnumRegula.PACKAGE, "\\bpackage\\s+([\\w.]+);", false);
        addRegexRule(BlockEnumRegula.IMPORT, "\\bimport\\s+(?:static\\s+)?([\\w.*]+);", false);
               // ===== 1. 注释 =====
        // 单行注释
        addRegexRule(BlockEnumRegula.COMMENT, "//.*", false);
        // 多行注释
        addRegexRule(BlockEnumRegula.COMMENT, "/\\*[\\s\\S]*?\\*/", true);
        // Javadoc 文档注释
        addRegexRule(BlockEnumRegula.DOC_COMMENT, "/\\*\\*[\\s\\S]*?\\*/", true);
// ===== 5. 注解 =====
        addRegexRule(BlockEnumRegula.ANNOTATION, "@[\\w.]+(?:\\s*\\([^)]*\\))?", true);

        // ===== 2. 字符串 =====
        // 双引号字符串（支持转义）
        addRegexRule(BlockEnumRegula.STRING, "\"(?:[^\"\\\\]|\\\\.)*\"", true);
        // 单引号字符串（支持转义）
        addRegexRule(BlockEnumRegula.STRING, "'(?:[^'\\\\]|\\\\.)*'", true);
        // 文本块（支持多行）
        addRegexRule(BlockEnumRegula.STRING, "\"\"\"(?:[^\"]|\"[^\"]|\"\"[^\"])*?\"\"\"", true);


    }
}