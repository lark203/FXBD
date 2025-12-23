package com.xx.UI.complex.textArea.content.listener;

import com.xx.UI.complex.textArea.content.BDTextAreaContent;

import java.util.EventObject;

public class SelectRangeChangeEvent extends EventObject {
    private final BDTextAreaContent.Point oldStartPoint;
    private final BDTextAreaContent.Point oldEndPoint;
    private final BDTextAreaContent.Point newStartPoint;
    private final BDTextAreaContent.Point newEndPoint;

    public SelectRangeChangeEvent(BDTextAreaContent source, BDTextAreaContent.Point oldStartPoint, BDTextAreaContent.Point oldEndPoint, BDTextAreaContent.Point newStartPoint, BDTextAreaContent.Point newEndPoint) {
        super(source);
        this.oldStartPoint = oldStartPoint;
        this.oldEndPoint = oldEndPoint;
        this.newStartPoint = newStartPoint;
        this.newEndPoint = newEndPoint;
    }

    public BDTextAreaContent.Point getNewEndPoint() {
        return newEndPoint;
    }

    public BDTextAreaContent.Point getNewStartPoint() {
        return newStartPoint;
    }

    public BDTextAreaContent.Point getOldEndPoint() {
        return oldEndPoint;
    }

    public BDTextAreaContent.Point getOldStartPoint() {
        return oldStartPoint;
    }

    @Override
    public BDTextAreaContent getSource() {
        return (BDTextAreaContent) super.getSource();
    }
}
