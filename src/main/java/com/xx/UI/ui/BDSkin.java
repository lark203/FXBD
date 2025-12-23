package com.xx.UI.ui;

import com.xx.UI.util.BDMapping;
import javafx.scene.control.SkinBase;

public abstract class BDSkin<T extends BDControl> extends SkinBase<T> implements BDUI {
    protected final BDMapping mapping;
    protected final T control;
    protected BDSkin(T t) {
        super(t);
        this.control = t;
        this.mapping = t.getMapping();
        initUI();
        initEvent();
        initProperty();
    }
}
