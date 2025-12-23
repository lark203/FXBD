package com.xx.UI.complex.textArea.content.segment;

import java.io.Serializable;
import java.util.List;

/**
 * 文本内容段
 */
public final class TextSegment extends Segment<StringBuilder> {

    public TextSegment(String s) {
        super(new StringBuilder(s), StringBuilder::toString);
    }

    @Override
    protected void replace(int start, int end, String text, List<Segment<? >> segments) {
        var.replace(start, end, text);
    }

    @Override
    protected void insertSegment(int index, Segment<? > newSegment, List<Segment<? >> segments) {
        String left = var.substring(0, index);
        String right = var.substring(index);
        int i = segments.indexOf(this);
        segments.set(i, new TextSegment(left));
        segments.add(newSegment);
        segments.add(i + 2, new TextSegment(right));
    }
    public void insert(int index,String s){
        var.insert(index,s);
    }
    @Override
    public TextSegment getSegment(int start, int end) {
        if (start < 0 || end > var.length() || start > end) {
            throw new IndexOutOfBoundsException(
                    String.format("Invalid Range [%d, %d] in text of length %d", start, end, var.length()));
        }
        return new TextSegment(var.substring(start, end));
    }

    @Override
    public TextSegment clone() {
        return new TextSegment(var.toString());
    }
}