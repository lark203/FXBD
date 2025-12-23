package com.xx.UI.complex.textArea.content.listener;

import java.io.Serializable;
import java.util.EventListener;

public interface ContentChangeListener extends EventListener , Serializable {
    void contentChanged(ContentChangeEvent event);
}
