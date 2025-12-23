package com.xx.UI.complex.textArea.view.dataFormat.example.regex;

import com.xx.UI.complex.textArea.view.BDTextArea;
import com.xx.UI.complex.textArea.view.dataFormat.analyse.BDTextInitFactory;
import com.xx.UI.complex.textArea.view.dataFormat.analyse.DataBlock;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class BDRegulaInitFactory extends BDTextInitFactory<BlockEnumRegula> {

    public BDRegulaInitFactory(BDTextArea textArea) {
        super(textArea, new BDRegexAnalyse(textArea.toString()));
    }

    @Override
    public void renderingText(Text text, Region textPane, DataBlock<BlockEnumRegula, ? > dataBlock) {
        // 设置默认字体
        text.getStyleClass().setAll("BDTextarea-java-text");

        // 基于IDEA的Darcula主题配色方案
        switch (dataBlock.getType()) {
            case STRING: // 字符串 - 绿色
                text.setFill(Color.web("#067D17"));
                break;

            case COMMENT:
            case DOC_COMMENT: // 单行注释 - 灰色
                text.setFill(Color.web("#8C8C8C"));
                break;

            case KEYWORD:
            case CONTROL_FLOW:
            case ANONYMOUS_CLASS:
            case SEALED:
            case YIELD:
            case IMPORT:
            case FIELD:
            case VOLATILE:
            case ASSERTION:
            case PERMITS:
            case MODIFIER:
            case SYNCHRONIZED:
            case MODULE:
            case OPERATOR:
            case PACKAGE:
            case TRANSIENT:
            case NATIVE:
            case NON_SEALED:
            case RECORD:
            case VAR:// 关键字 - 紫色
                text.setFill(Color.web("#0033B3"));
                break;

            case DATATYPE: // 数据类型 - 浅蓝色
                text.setFill(Color.web("#871094"));
                break;

            case ANNOTATION:
            case OVERRIDE:
            case FUNCTIONAL_INTERFACE: // 注解 - 黄色
                text.setFill(Color.web("#9E880D"));
                break;

            case METHOD_DEF: // 方法定义 - 黄色
                text.setFill(Color.web("#00627A"));
                break;

            case CONSTANT: // 常量 - 橙色
                text.setFill(Color.web("#871094"));
                break;

            case SPECIAL_SYNTAX: // 特殊语法 - 紫色
                text.setFill(Color.web("#9876AA"));
                break;

            case EXCEPTION: // 异常类型 - 红色
                text.setFill(Color.web("#FF6B68"));
                break;

            case GENERIC: // 泛型 - 浅蓝色
                text.setFill(Color.web("#4EC9B0"));
                break;

            case STATIC_MEMBER: // 静态成员 - 斜体
                text.setFill(Color.web("#871094"));
                break;


            case ENUM_CONSTANT: // 枚举常量 - 常量颜色
                text.setFill(Color.web("#871094"));
                break;

            case RECORD_COMPONENT: // Record组件 - 字段颜色
                text.setFill(Color.web("#871094"));
                break;

            case LAMBDA: // Lambda表达式 - 特殊语法颜色
                text.setFill(Color.web("#9876AA"));
                break;

            case TYPE_CAST: // 类型转换 - 特殊语法颜色
                text.setFill(Color.web("#9876AA"));
                break;


            case THREAD: // 线程相关 - 特殊语法颜色
                text.setFill(Color.web("#9876AA"));
                break;

            case DEPRECATED: // @Deprecated标记 - 删除线样式
                text.setFill(Color.web("#808080"));
                text.setStrikethrough(true);
                break;

// 未定义类型 - 红色（便于调试）
            case UNDEFINED:
            case CLASS_DEF:
            case PARAMETER:
            case METHOD_CALL:
            default:
                break;
        }
    }

    @Override
    public void renderingNode(Node node,Region nodePane, DataBlock<BlockEnumRegula, ? > dataBlock) {
    }
}