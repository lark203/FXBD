package com.xx.UI.complex.splitPane;

import com.xx.UI.ui.BDSkin;
import javafx.scene.layout.StackPane;

public class BDSplitPaneSkin extends BDSkin<BDSplitPane> {
    private final StackPane root;

    protected BDSplitPaneSkin(BDSplitPane bdSplitPane) {
        root = new StackPane();
        super(bdSplitPane);
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void initProperty() {
        mapping.addListener(() -> {
            root.getChildren().setAll(control.splitItem.get());
            control.splitItem.get().splitPane = control;
        }, true, control.splitItem);
    }

    @Override
    public void initUI() {
        getChildren().setAll(root);
    }
}
