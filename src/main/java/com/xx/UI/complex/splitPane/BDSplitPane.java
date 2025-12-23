package com.xx.UI.complex.splitPane;

import com.xx.UI.ui.BDControl;
import com.xx.UI.ui.BDSkin;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class BDSplitPane extends BDControl {
    final SimpleIntegerProperty tabsCount = new SimpleIntegerProperty(0);
    final SimpleObjectProperty<BDSplitItem> splitItem = new SimpleObjectProperty<>();

    public BDSplitPane(BDSplitItem root) {
        splitItem.set(root);
        tabsCount.set(root.getTabs().size());
        mapping.addDisposeEvent(() -> {
            if (splitItem.get() != null)
                splitItem.get().getMapping().dispose();
        });
    }

    public BDSplitItem getRoot() {
        return splitItem.get();
    }

    void setRoot(BDSplitItem root) {
        if (root == null) {
            throw new IllegalArgumentException("Root cannot be null");
        }

        // 清理旧根节点
        if (splitItem.get() != null) {
            splitItem.get().splitPane = null;
        }

        splitItem.set(root);
        root.splitPane = this;
    }

    public ReadOnlyIntegerProperty tabsCountProperty() {
        return tabsCount;
    }

    public int getTabsCount() {
        return tabsCount.get();
    }

    @Override
    protected BDSkin<? extends BDControl> createDefaultSkin() {
        return new BDSplitPaneSkin(this);
    }
}
