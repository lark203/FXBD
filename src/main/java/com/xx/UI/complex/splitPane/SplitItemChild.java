package com.xx.UI.complex.splitPane;

public record SplitItemChild(BDSplitItem first, BDSplitItem second) {
    public SplitItemChild {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Both child items must be non-null");
        }
    }
}
