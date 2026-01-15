package com.xx.UI.complex.stage;

import com.xx.UI.ui.BDSkin;
import com.xx.UI.util.Util;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

import static com.xx.UI.complex.stage.BDContent.DRAG_ITEM;

public class BDContentSkin extends BDSkin<BDContent> {

    private BDSidebar tempSidebar;

    protected BDContentSkin(BDContent bdContent) {
        super(bdContent);
    }

    @Override
    public void initUI() {
        getChildren().setAll(control.borderPane);
        HBox.setHgrow(control.verticalSplitPane, Priority.ALWAYS);
        AnchorPane.setTopAnchor(control.horizontalSplitPane,.0);
        AnchorPane.setRightAnchor(control.horizontalSplitPane,.0);
        AnchorPane.setBottomAnchor(control.horizontalSplitPane,.0);
        AnchorPane.setLeftAnchor(control.horizontalSplitPane,.0);
        control.verticalSplitPane.setOrientation(Orientation.VERTICAL);
        control.leftSplitPane.setOrientation(Orientation.VERTICAL);
        control.rightSplitPane.setOrientation(Orientation.VERTICAL);
        control.borderPane.getStyleClass().add("bd-content-pane");
        control.verticalSplitPane.getStyleClass().add("bd-content-vertical-pane");
        control.horizontalSplitPane.getStyleClass().add("bd-content-horizontal-pane");
        control.leftSplitPane.getStyleClass().add("bd-content-left-pane");
        control.rightSplitPane.getStyleClass().add("bd-content-right-pane");
        control.bottomSplitPane.getStyleClass().add("bd-content-bottom-pane");
        control.centerPane.getStyleClass().add("bd-content-center-pane");
        control.splitBack.getStyleClass().add("bd-split-back");
        control.splitBack.setMouseTransparent(true);
    }

    @Override
    public void initProperty() {
        mapping.addListener(() -> {
            control.centerPane.getChildren().clear();
            if (control.getContent() != null)
                control.centerPane.getChildren().add(control.getContent());
        }, true, control.contentProperty());
    }

    @Override
    public void initEvent() {
        mapping.addEventFilter(control.borderPane, DragEvent.DRAG_OVER, event -> {
                    if (DRAG_ITEM == null || !control.acceptDragItem(DRAG_ITEM)) return;
                    event.consume();
                    event.acceptTransferModes(TransferMode.MOVE);
                    if (Util.searchEventTargetNode(event.getTarget(), BDSidebar.class) instanceof BDSidebar sidebar && (sidebar.direction.equals(BDDirection.LEFT) || sidebar.direction.equals(BDDirection.RIGHT))) {
                        control.changeSplitBack(calculate(event, sidebar));
                    } else {
                        Bounds bounds = control.horizontalSplitPane.localToScene(control.horizontalSplitPane.getLayoutBounds());
                        control.refreshDivider();
                        if (!control.leftSideBar.isNone() && event.getSceneX() <= bounds.getMinX() + bounds.getWidth() * control.leftDivider)
                            control.changeSplitBack(calculate(event, control.leftSideBar.get()));
                        else if (!control.rightSideBar.isNone() && event.getSceneX() >= bounds.getMinX() + bounds.getWidth() * control.rightDivider)
                            control.changeSplitBack(calculate(event, control.rightSideBar.get()));
                        else
                            control.hideAllItemBack();
                    }
                })
                .addEventFilter(control.borderPane, DragEvent.DRAG_EXITED, event -> {
                    if (DRAG_ITEM == null || !control.acceptExitItem(DRAG_ITEM)) return;
                    control.hideAllItemBack();
                    if (tempSidebar != null)
                        tempSidebar.removeItemNode(tempSidebar.itemBack);
                })
                .addEventFilter(control.borderPane, DragEvent.DRAG_DROPPED, event -> {
                    if (DRAG_ITEM == null || !control.acceptDragDropped(DRAG_ITEM)) return;
                    BDSidebar.BDDragData bdDragData = null;
                    if (Util.searchEventTargetNode(event.getTarget(), BDSidebar.class) instanceof BDSidebar sidebar && (sidebar.direction.equals(BDDirection.LEFT) || sidebar.direction.equals(BDDirection.RIGHT))) {
                        bdDragData = calculate(event, sidebar);
                    } else {
                        Bounds bounds = control.horizontalSplitPane.localToScene(control.horizontalSplitPane.getLayoutBounds());
                        control.refreshDivider();
                        if (!control.leftSideBar.isNone() && event.getSceneX() <= bounds.getMinX() + bounds.getWidth() * control.leftDivider)
                            bdDragData = calculate(event, control.leftSideBar.get());
                        else if (!control.rightSideBar.isNone() && event.getSceneX() >= bounds.getMinX() + bounds.getWidth() * control.rightDivider)
                            bdDragData = calculate(event, control.rightSideBar.get());
                    }

                    BDSidebar oldSidebar = DRAG_ITEM.sidebar.get();
                    if (bdDragData == null)
                        oldSidebar.cleanBDSideBarItem(DRAG_ITEM);
                    else {
                        BDSidebar newSideBar = null;
                        if (bdDragData.direction().equals(BDDirection.LEFT))
                            newSideBar = control.leftSideBar.get();
                        else if (bdDragData.direction().equals(BDDirection.RIGHT))
                            newSideBar = control.rightSideBar.get();
                        else if (bdDragData.direction().equals(BDDirection.BOTTOM)) {
                            if (bdDragData.inSequence().equals(BDInSequence.FRONT))
                                newSideBar = control.leftSideBar.get();
                            else newSideBar = control.rightSideBar.get();
                        }
                        if (newSideBar == null)
                            throw new IllegalArgumentException("DRAG_ITEM的DIRECTION不能为：" + bdDragData.direction());
                        if (!bdDragData.direction().equals(DRAG_ITEM.getDirection())
                                || !DRAG_ITEM.getInSequence().equals(bdDragData.inSequence()))
                            oldSidebar.cleanBDSideBarItem(DRAG_ITEM);
                        DRAG_ITEM.sidebar.set(newSideBar);
                        DRAG_ITEM.setDirection(bdDragData.direction());
                        DRAG_ITEM.setInSequence(bdDragData.inSequence());
                        DRAG_ITEM.tempIndex = bdDragData.index();
                    }
                    control.hideAllItemBack();
                });
    }

    private BDSidebar.BDDragData calculate(DragEvent event, BDSidebar sidebar) {
        if (tempSidebar != sidebar) {
            if (tempSidebar != null)
                tempSidebar.removeItemNode(tempSidebar.itemBack);
            tempSidebar = sidebar;
        }
        BDSidebar.BDDragData bdDragData = sidebar.calculateDragData(event.getSceneY());
        if (bdDragData == null) sidebar.hideItemBack();
        else {
            sidebar.addItemNode(bdDragData.direction(), bdDragData.inSequence(), bdDragData.index(), sidebar.itemBack);
            sidebar.itemBack.setHeight(DRAG_ITEM.cachedHeight);
            sidebar.itemBack.setWidth(DRAG_ITEM.cachedWidth);
        }
        return bdDragData;
    }
}
