package com.xx.UI.complex.textArea.content.listener;

import com.xx.UI.complex.textArea.content.BDTextAreaContent;

import java.util.EventObject;

public class CaretChangeEvent extends EventObject {
    private final BDTextAreaContent.Point oldPoint;
    private final BDTextAreaContent.Point newPoint;
    public CaretChangeEvent(BDTextAreaContent source, BDTextAreaContent.Point oldPoint, BDTextAreaContent.Point newPoint) {
        super(source);
        this.oldPoint = oldPoint;
        this.newPoint = newPoint;
    }

    public BDTextAreaContent.Point getOldPoint() {
        return oldPoint;
    }

    public BDTextAreaContent.Point getNewPoint() {
        return newPoint;
    }

    @Override
    public BDTextAreaContent getSource() {
        return (BDTextAreaContent) source;
    }
}
