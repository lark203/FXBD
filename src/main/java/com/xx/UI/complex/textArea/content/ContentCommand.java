package com.xx.UI.complex.textArea.content;

import com.xx.UI.complex.textArea.content.BDTextAreaContent.Point;
public abstract class ContentCommand implements Command{
    protected BDTextAreaContent content;
    protected boolean fireEvent;
    protected Point startCaret, endCaret;
    public ContentCommand(Point startCaret,BDTextAreaContent content,boolean fireEvent) {
        this.content = content;
        this.fireEvent = fireEvent;
        this.startCaret = startCaret;
    }
    abstract Point getEndPoint();
}
