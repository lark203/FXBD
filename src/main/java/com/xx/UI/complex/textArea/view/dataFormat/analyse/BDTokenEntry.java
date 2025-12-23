package com.xx.UI.complex.textArea.view.dataFormat.analyse;

import com.xx.UI.complex.textArea.content.segment.Paragraph;
import com.xx.UI.complex.textArea.content.segment.Segment;

import java.util.ArrayList;
import java.util.List;

/**
 * BDTokenEntry用于封装一个token的信息，包括段落索引、起始位置、文本、类型
 */
public class BDTokenEntry<T extends Enum<?> & Analyse.BDTextEnum<T>> implements Comparable<BDTokenEntry<T>> {
    private final T type;
    //    前闭后开
    private int start;
    private int end;
    private final Object info;

    public BDTokenEntry(int start, int end, T type,Object info) {
        if (start < 0) throw new IllegalArgumentException("起始位置：%d不能小于0".formatted(start));
        if (end < 0) throw new IllegalArgumentException("结束位置：%d不能小于0".formatted(end));
        if (start > end) throw new IllegalArgumentException("起始位置：%d不能大于或等于结束位置：%d".formatted(start, end));
        if (type == null) throw new IllegalArgumentException("类型不能为空");
        this.start = start;
        this.end = end;
        this.type = type;
        this.info = info;
    }

    //    前闭后开
    public void slicing(int start, int end, BDTokenEntryList<T> tokenEntryList) {
        if (start < this.start)
            throw new IllegalArgumentException("切割起始位置：%d不能小于当前token的起始位置：%d".formatted(start, this.start));
        if (end > this.end)
            throw new IllegalArgumentException("切割结束位置：%d不能大于当前token的结束位置：%d".formatted(end, this.end));
        if (start == this.start && end == this.end) tokenEntryList.removeTokenEntry(this);
        else if (start == this.start) {
            this.start = end;
        } else if (end == this.end) {
            this.end = start;
        } else {
            BDTokenEntry<T> prevToken = new BDTokenEntry<>(this.start, start, this.type,info);
            BDTokenEntry<T> nextToken = new BDTokenEntry<>(end, this.end, this.type,info);
            tokenEntryList.removeTokenEntry(this);
            tokenEntryList.addTokenEntry(prevToken);
            tokenEntryList.addTokenEntry(nextToken);
        }
    }

    public T getType() {
        return type;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public Object getInfo() {
        return info;
    }

    public List<DataBlock<T, ? >> transferToDataBlock(Paragraph paragraph) {
        List<DataBlock<T, ? >> blocks = new ArrayList<>();
        for (Segment<?> segment : paragraph.getParagraph(start, end).getSegments()) {
            blocks.add(new DataBlock<>(type, segment, info));
        }
        return blocks;
    }

    @Override
    public int compareTo(BDTokenEntry<T> o) {
        if (this.start < o.end && o.start < this.end)
            throw new IllegalArgumentException("两个token不能有交集,entry1: %s, entry2: %s".formatted(this, o));
        return Integer.compare(this.start, o.start);
    }

    @Override
    public String toString() {
        return "BDTokenEntry{" +
                "start=" + start +
                ", end=" + end +
                ", type=" + type +
                ", info=" + info +
                '}';
    }
}
