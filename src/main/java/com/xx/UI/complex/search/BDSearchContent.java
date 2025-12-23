package com.xx.UI.complex.search;

import com.xx.UI.ui.BDControl;
import com.xx.UI.ui.BDSkin;

public class BDSearchContent extends BDControl {

    @Override
    protected BDSkin<? extends BDControl> createDefaultSkin() {
        return new BDSearchContentSkin(this);
    }
}
