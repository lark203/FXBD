package com.xx.UI.complex.search;

import javafx.beans.property.IntegerProperty;

import java.util.List;
import java.util.Map;

public abstract class BDSearchResource {
    final BDSearchPane searchPane;

    public BDSearchResource(BDSearchPane searchPane) {
        this.searchPane = searchPane;
    }

    protected abstract String getResource();

    protected abstract String getSelectedResource();
    protected abstract int getSelectedStartParagraph();
    protected abstract int getSelectedOffset();
    protected abstract IntegerProperty resultIndexProperty();
    protected abstract void updateResult(int searchBlockIndex, List<BDSearchBox.SearchBlock> searchBlocks,Map<Integer,List<BDSearchBox.SearchResult>> resultMap);

}