package com.xx.UI.complex.textArea.content.listener;

import java.io.Serializable;
import java.util.EventListener;

public interface CaretChangeListener extends EventListener, Serializable {
    void caretChanged(CaretChangeEvent e);
}
