package com.xx.UI.complex.textArea.view.dataFormat.example.java;

import com.xx.UI.complex.textArea.view.BDTextArea;
import com.xx.UI.complex.textArea.view.dataFormat.analyse.BDTextInitFactory;
import com.xx.UI.complex.textArea.view.dataFormat.analyse.DataBlock;
import com.xx.UI.complex.textArea.view.dataFormat.example.java.JavaImp.BlockEnumJava;
import com.xx.UI.complex.textArea.view.dataFormat.mark.MARK_DIRECTION;
import com.xx.UI.complex.textArea.view.dataFormat.mark.Mark;
import com.xx.UI.complex.textArea.view.dataFormat.mark.MarkNode;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class BDJavaTextInitFactory extends BDTextInitFactory<BlockEnumJava> {

    public BDJavaTextInitFactory(BDTextArea textArea) {
        super(textArea, new BDAnalyseJava());
    }
    @Override
    public void renderingText(Text text, Region textPane, DataBlock<BlockEnumJava, ?> dataBlock) {
        // 先设置基础样式
        text.getStyleClass().setAll("BDTextarea-java-text");

        // 根据不同的代码块类型设置特定样式
        switch (dataBlock.getType()) {
            // 关键字 - 使用强调色+粗体
            case EXPORTS:
            case MODULE:
            case NONSEALED:
            case OPEN:
            case OPENS:
            case PERMITS:
            case PROVIDES:
            case RECORD:
            case REQUIRES:
            case SEALED:
            case TO:
            case TRANSITIVE:
            case USES:
            case VAR:
            case WITH:
            case YIELD:
            case ABSTRACT:
            case ASSERT:
            case BOOLEAN:
            case BREAK:
            case BYTE:
            case CASE:
            case CATCH:
            case CHAR:
            case CLASS:
            case CONST:
            case CONTINUE:
            case DEFAULT:
            case DO:
            case ELSE:
            case ENUM:
            case EXTENDS:
            case FINAL:
            case FINALLY:
            case FLOAT:
            case FOR:
            case IF:
            case GOTO:
            case IMPLEMENTS:
            case IMPORT:
            case INSTANCEOF:
            case INT:
            case INTERFACE:
            case LONG:
            case NATIVE:
            case NEW:
            case PACKAGE:
            case PRIVATE:
            case PROTECTED:
            case PUBLIC:
            case RETURN:
            case SHORT:
            case STATIC:
            case STRICTFP:
            case SUPER:
            case SWITCH:
            case SYNCHRONIZED:
            case THIS:
            case THROW:
            case THROWS:
            case TRANSIENT:
            case TRY:
            case VOID:
            case VOLATILE:
            case WHILE:
            case BooleanLiteral:
            case NullLiteral:
                text.getStyleClass().add("keyword");
                break;
            case IntegerLiteral:
                text.getStyleClass().add("number");
                if (dataBlock.getMark() == null)
                    dataBlock.setMark(new Mark(dataBlock, new MarkNode() {
                        {
                            setText("Integer");
                        }
                    }, MARK_DIRECTION.LEFT));
                break;
            case DOUBLE:
                text.getStyleClass().add("number");
                if (dataBlock.getMark() == null)
                    dataBlock.setMark(new Mark(dataBlock, new MarkNode() {
                        {
                            setText("Double");
                        }
                    }, MARK_DIRECTION.LEFT));
                break;
            case FloatingPointLiteral:
                text.getStyleClass().add("number");
                if (dataBlock.getMark() == null)
                    dataBlock.setMark(new Mark(dataBlock, new MarkNode() {
                        {
                            setText("Float");
                        }
                    }, MARK_DIRECTION.LEFT));
                break;
            case CharacterLiteral:
                text.getStyleClass().add("number");
                if (dataBlock.getMark() == null)
                    dataBlock.setMark(new Mark(dataBlock, new MarkNode() {
                        {
                            setText("Character");
                        }
                    }, MARK_DIRECTION.LEFT));
                break;
            case StringLiteral:
            case TextBlock:
                text.getStyleClass().add("string");
                if (dataBlock.getMark() == null)
                    dataBlock.setMark(new Mark(dataBlock, new MarkNode() {
                        {
                            setText("String");
                        }
                    }, MARK_DIRECTION.LEFT));
                textPane.getStyleClass().add("string");
                break;
            // 运算符和分隔符 - 使用默认强调色
            case LPAREN:
            case RPAREN:
            case LBRACE:
            case RBRACE:
            case LBRACK:
            case RBRACK:
            case SEMI:
            case COMMA:
            case DOT:
            case ELLIPSIS:
            case COLONCOLON:
            case ASSIGN:
            case GT:
            case LT:
            case BANG:
            case TILDE:
            case QUESTION:
            case COLON:
            case ARROW:
            case EQUAL:
            case LE:
            case GE:
            case NOTEQUAL:
            case AND:
            case OR:
            case INC:
            case DEC:
            case ADD:
            case SUB:
            case MUL:
            case DIV:
            case BITAND:
            case BITOR:
            case CARET:
            case MOD:
            case ADD_ASSIGN:
            case SUB_ASSIGN:
            case MUL_ASSIGN:
            case DIV_ASSIGN:
            case AND_ASSIGN:
            case OR_ASSIGN:
            case XOR_ASSIGN:
            case MOD_ASSIGN:
            case LSHIFT_ASSIGN:
            case RSHIFT_ASSIGN:
            case URSHIFT_ASSIGN:
            case OACA:
                break;

            // 注释 - 使用柔和文本色
            case COMMENT:
            case LINE_COMMENT:
                text.getStyleClass().add("comment");
                break;

            // 标识符 - 使用基础样式（已包含）
            case Identifier:
                text.getStyleClass().add("identifier");
                break;
            case UNDER_SCORE:
                break;

            case AT:
                text.getStyleClass().add("annotation");
                break;
            case METHOD_NAME:
                text.getStyleClass().add("method-name");
                break;
            case FIELD_NAME:
                text.getStyleClass().add("field-name");
                break;
            case ERROR:
                text.getStyleClass().add("error");
                if (dataBlock.getInfo()!= null) {
                    Tooltip tooltip = new Tooltip(dataBlock.getInfo().toString());
                    text.setOnMouseMoved(_ -> {
                        Bounds bounds = text.localToScreen(text.getLayoutBounds());
                        tooltip.show(text.getScene().getWindow(), bounds.getMaxX(), bounds.getMaxY());
                    });
                    text.setOnMouseExited(e -> tooltip.hide());
                }
                break;
            // 未定义和其他类型
            case UNDEFINED:
            default:
                break;
        }
    }

    @Override
    public void renderingNode(Node node, Region nodePane, DataBlock<BlockEnumJava, ? > dataBlock) {

    }
}
