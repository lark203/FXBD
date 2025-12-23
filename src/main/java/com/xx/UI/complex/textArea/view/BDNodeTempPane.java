package com.xx.UI.complex.textArea.view;

import javafx.scene.Node;

/**
 * BDTempNodePane 专门用来包装Node节点，目的是保证Node的背景色
 */

public class BDNodeTempPane extends BDTempPane<Node> {
    public BDNodeTempPane(Node content) {
        super(content);
    }
}
