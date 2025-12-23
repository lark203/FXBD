package com.xx.UI.complex.textArea.view;

import com.xx.UI.ui.BDUI;
import com.xx.UI.util.BDMapping;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.skin.ListViewSkin;
import javafx.scene.control.skin.VirtualFlow;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BDTextAreaListViewSKin extends ListViewSkin<Object> implements BDUI {
    private final BDMapping mapping;
    private final BDTextArea textArea;
    private final BDTextAreaListView listView;
    private VirtualFlow<?> virtualFlow;
    private ScrollBar vBar;
    private ScrollBar hBar;


    public BDTextAreaListViewSKin(final BDTextAreaListView control) {
        super(control);
        mapping = control.mapping;
        this.textArea = control.textArea;
        this.listView = control;
        initUI();
        initEvent();
        initProperty();
    }

    @Override
    public void initUI() {
        virtualFlow = (VirtualFlow<?>) listView.lookup(".virtual-flow");
        virtualFlow.setFixedCellSize(-1);
        virtualFlow.setPannable(true);
        virtualFlow.lookupAll(".scroll-bar").forEach(e -> {
            if (e instanceof ScrollBar) {
                if (((ScrollBar) e).getOrientation().equals(Orientation.VERTICAL))
                    vBar = (ScrollBar) e;
                else
                    hBar = (ScrollBar) e;
            }
        });
    }

    @Override
    public void initEvent() {
        BDUI.super.initEvent();
    }
    IndexedCell<?> firstVisibleCell(){
        return virtualFlow.getFirstVisibleCell();
    }
    IndexedCell<?> lastVisibleCell(){
        return virtualFlow.getLastVisibleCell();
    }
    @Override
    public void initProperty() {
        BDTextAreaListView.CaretBlinking blinking = new BDTextAreaListView.CaretBlinking(listView.caretBlink, mapping);
        listView.content.addCaretChangeListener(_ -> blinking.playFromStart());
        mapping.binding(listView.caretVisible,
                        Bindings.createBooleanBinding(() -> listView.caretBlink.get() &&
                                        textArea.isDisplayCaret() &&
                                        textArea.isFocusedWith() &&
                                        !textArea.isDisabled(),
                                listView.caretBlink,
                                textArea.displayCaretProperty(),
                                textArea.focusedWithProperty(),
                                textArea.disabledProperty()))
                .bindBidirectional(listView.verticalScroll,vBar.valueProperty())
                .bindBidirectional(listView.horizontalScroll,hBar.valueProperty())
                .bindBidirectional(listView.horizontalScrollMax,hBar.maxProperty())
                .bindProperty(virtualFlow.pannableProperty(), listView.textArea.pannableProperty())
                .addListener(() -> refreshScroll().run(), true, listView.getSelectionModel().selectedIndexProperty());
        Platform.runLater(() -> refreshScroll().run());
        textArea.refreshScroll = refreshScroll();
    }

    private Runnable refreshScroll() {
        return () -> {
            IndexedCell<?> startCell = virtualFlow.getFirstVisibleCell();
            IndexedCell<?> endCell = virtualFlow.getLastVisibleCell();
            if (startCell != null && endCell != null) {
                int selectedIndex = listView.getSelectionModel().getSelectedIndex();
                if (selectedIndex <= startCell.getIndex() + 1) {
                    listView.scrollTo(Math.max(selectedIndex - 1, 0));
                } else if (selectedIndex >= endCell.getIndex() - 1) {
                    listView.scrollTo(Math.min(selectedIndex - (endCell.getIndex() - startCell.getIndex()) + 2, virtualFlow.getCellCount() - 1));
                }
            }
        };
    }
}
