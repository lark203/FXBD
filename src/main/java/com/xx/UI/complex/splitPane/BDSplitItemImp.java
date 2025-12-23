package com.xx.UI.complex.splitPane;

public interface BDSplitItemImp {
    boolean canAdd();

    boolean addItem(SplitDir dir, BDTab... item);

    boolean removeItem(BDSplitItem item);

    boolean removeItemFromParent();

    default boolean acceptDrag(BDTab tab) {
        return true;
    }

    void check();
    BDSplitItem initItem();
}
