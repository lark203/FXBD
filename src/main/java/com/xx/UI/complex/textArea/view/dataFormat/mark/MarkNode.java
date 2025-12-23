package com.xx.UI.complex.textArea.view.dataFormat.mark;

import javafx.scene.control.Label;

/**
 * 为了与其他的Node区分
 */
public class MarkNode extends Label {
    private static final String CSS_CLASS_NAME = "bd-mark-node";
    private Mark mark;

    public MarkNode() {
        getStyleClass().add(CSS_CLASS_NAME);
    }

    public Mark getMark() {
        return mark;
    }

    void setMark(Mark mark) {
        this.mark = mark;
    }
}
