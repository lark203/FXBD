package com.xx.UI.complex.textArea.content.listener;

import com.xx.UI.complex.textArea.content.BDTextAreaContent;
import com.xx.UI.complex.textArea.content.segment.Paragraph;
import com.xx.UI.complex.textArea.content.segment.Segment;

import java.util.EventObject;
import java.util.List;

public class ContentChangeEvent extends EventObject {
    private final ChangeType changeType;
    private final int startParaIndex;
    private final int startOffset;
    private final int endParaIndex;
    private final int endOffset;
    //    当前操作的段落集合
    private final List<Paragraph> changedParagraphs;
    //    当有replace操作时，changedSegment为插入内容，否则一般为null
    private final Segment<?> changedSegment;

    public ContentChangeEvent(BDTextAreaContent source, ChangeType changeType,
                              int startParaIndex, int endParaIndex, List<Paragraph> changedParagraphs,
                              int startOffset, int endOffset) {
        super(source);
        this.changeType = changeType;
        this.startParaIndex = startParaIndex;
        this.endParaIndex = endParaIndex;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.changedParagraphs = changedParagraphs;
        this.changedSegment = null;
    }

    public ContentChangeEvent(BDTextAreaContent source, ChangeType changeType,
                              int startParaIndex, int endParaIndex, Segment<?> changedSegment, List<Paragraph> changedParagraphs,
                              int startOffset, int endOffset) {
        super(source);
        this.changeType = changeType;
        this.startParaIndex = startParaIndex;
        this.endParaIndex = endParaIndex;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.changedSegment = changedSegment;
        this.changedParagraphs = changedParagraphs;
    }

    // Getters
    public ChangeType getChangeType() {
        return changeType;
    }

    public int getStartParaIndex() {
        return startParaIndex;
    }

    public int getEndParaIndex() {
        return endParaIndex;
    }

    public Segment<?> getChangedSegment() {
        return changedSegment;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public List<Paragraph> getChangedParagraphs() {
        return changedParagraphs;
    }

    @Override
    public String toString() {
        return "ContentChangeEvent{" +
                "changedParagraphs=" + changedParagraphs +
                ", changeType=" + changeType +
                ", startParaIndex=" + startParaIndex +
                ", startOffset=" + startOffset +
                ", endParaIndex=" + endParaIndex +
                ", endOffset=" + endOffset +
                ", changedSegment=" + changedSegment
                + '}';
    }

    @Override
    public BDTextAreaContent getSource() {
        return (BDTextAreaContent) super.getSource();
    }

    public enum ChangeType {INSERT, DELETE, REPLACE}
}

