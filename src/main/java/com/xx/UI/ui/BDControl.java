package com.xx.UI.ui;

import com.xx.UI.util.BDMapping;
import javafx.scene.control.Control;

public abstract class BDControl extends Control {
    protected final BDMapping mapping = new BDMapping();

    @Override
    abstract protected BDSkin<? extends BDControl> createDefaultSkin();

    public BDMapping getMapping() {
        return mapping;
    }
}
