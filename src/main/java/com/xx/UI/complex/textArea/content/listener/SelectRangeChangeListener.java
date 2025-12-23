package com.xx.UI.complex.textArea.content.listener;

import java.io.Serializable;
import java.util.EventListener;

public interface SelectRangeChangeListener extends EventListener, Serializable {
    void selectRangeChanged(SelectRangeChangeEvent event);
}
