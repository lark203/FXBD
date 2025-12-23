package com.xx.UI.complex.textArea.view;

import com.xx.UI.ui.BDSkin;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;


public class BDTextAreaSkin extends BDSkin<BDTextArea> {

    private BDTextAreaListView listView;

    protected BDTextAreaSkin(BDTextArea bdTextArea) {
        super(bdTextArea);
    }

    @Override
    public void initUI() {
        super.initUI();listView = control.listView;
        getChildren().setAll(listView);
        Platform.runLater(() -> listView.requestFocus());
    }

    @Override
    public void initEvent() {
        super.initEvent();
        /* 由于control实际上未获得focus所以这里需要在listView上注册Key_Pressed事件,因为这个事件需要focus才会触发。使用eventHandel的原因是允许节点的消费*/
        mapping
                .addEventHandler(listView, KeyEvent.KEY_TYPED, event -> {
                    BDTextArea.HandleKeyEvent keyEvent = control.getHandleKeyEvent();
                    if (keyEvent.filterKeyTyped(control, event)) return;
                    keyEvent.handleKeyTyped(control, event);
                })
                .addEventHandler(listView, KeyEvent.KEY_PRESSED, event -> {
                    BDTextArea.HandleKeyEvent keyEvent = control.getHandleKeyEvent();
                    if (keyEvent.filterKeyPressed(control, event)) return;
                    keyEvent.handleKeyPressed(control, event);
                });
    }


    @Override
    public void initProperty() {
        super.initProperty();
        refreshLines();
        control.content.addChangeRunnable(this::refreshLines);
        mapping.addListener(() -> listView
                .getSelectionModel()
                .select(control.getCaretPosition().paragraph()), true, control.caretPositionProperty());
    }

    private void refreshLines() {
        int line = control.content.linesNum();
        int size = listView.getItems().size();
        if (line == size) return;
        if (size < line) {
            List<Object> list = new ArrayList<>(line);
            for (int i = size; i < line; i++)
                list.add(new Object());
            listView.getItems().addAll(list);
        } else listView.getItems().remove(line, size);

    }
}