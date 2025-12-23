package com.xx.UI.complex.textArea.view;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/** 专门用来封装cell的文本的pane，目的：背景色等的设置。*/
public abstract class BDTempPane<T extends Node> extends StackPane {
    private final T content;
    private final String BD_TEMP_PANE_STYLE_CLASS = "bd-text-area-temp-pane";
    public BDTempPane(T content) {
        this.content = content;
        getChildren().add(content);
        getStyleClass().setAll(BD_TEMP_PANE_STYLE_CLASS);
    }
    public T getContent() {
        return content;
    }
}
