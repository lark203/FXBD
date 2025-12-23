package com.xx.UI.complex.textArea.view.dataFormat.example.json;

import com.xx.UI.complex.textArea.view.BDTextArea;
import com.xx.UI.complex.textArea.view.dataFormat.analyse.BDTextInitFactory;
import com.xx.UI.complex.textArea.view.dataFormat.analyse.DataBlock;
import com.xx.UI.complex.textArea.view.dataFormat.example.json.imp.BlockEnumJson;
import com.xx.UI.complex.textArea.view.dataFormat.mark.MARK_DIRECTION;
import com.xx.UI.complex.textArea.view.dataFormat.mark.Mark;
import com.xx.UI.complex.textArea.view.dataFormat.mark.MarkNode;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class BDJsonTextInitFactory extends BDTextInitFactory<BlockEnumJson> {
    public BDJsonTextInitFactory(BDTextArea textArea) {
        super(textArea, new BDAnalyseJson());
    }

    @Override
    public void renderingText(Text text, Region textPane, DataBlock<BlockEnumJson, ?> dataBlock) {
        text.getStyleClass().setAll("BDTextarea-json-text");

        switch (dataBlock.getType()) {
            case NUMBER:
                text.getStyleClass().add("number");
                break;
            case STRING:
                text.getStyleClass().add("string");
                break;
            case T__6: // true
            case T__7: // false
                text.getStyleClass().add("boolean");
                break;
            case T__8: // null
                text.getStyleClass().add("null");
                break;
            case T__0: // {
                if (dataBlock.getMark() == null)
                    dataBlock.setMark(new Mark(dataBlock, new MarkNode() {
                        {
                            setText("Map");
                        }
                    }, MARK_DIRECTION.RIGHT));
                text.getStyleClass().add("bracket");
                break;
                case T__4: // [
                    if (dataBlock.getMark() == null)
                        dataBlock.setMark(new Mark(dataBlock, new MarkNode() {
                            {
                                setText("Array");
                            }
                        }, MARK_DIRECTION.RIGHT));
                    text.getStyleClass().add("bracket");
                    break;
            case T__2: // }
            case T__5: // ]
                text.getStyleClass().add("bracket");
                break;
            case T__1: // ,
            case T__3: // :
                text.getStyleClass().add("punctuation");
                break;
            case WS: // 空白字符
                // 通常不需要特殊样式，保持默认
                break;
            case KEY:
                text.getStyleClass().add("key");
                break;
            case UNDEFINED:
            default:
                text.getStyleClass().add("undefined");
                break;
        }
    }

    @Override
    public void renderingNode(Node node, Region nodePane, DataBlock<BlockEnumJson, ?> dataBlock) {

    }
}