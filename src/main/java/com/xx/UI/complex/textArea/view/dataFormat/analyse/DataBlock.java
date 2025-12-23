package com.xx.UI.complex.textArea.view.dataFormat.analyse;

import com.xx.UI.complex.textArea.content.segment.Segment;
import com.xx.UI.complex.textArea.view.dataFormat.mark.Mark;


/**
 * 用于存储不同类型的数据块。方便区分渲染。
 */
public class DataBlock<T extends Enum<?> & Analyse.BDTextEnum<T>, V> {
//    标示本数据块的类型。
    private final T type;
//    数据块内容。
    private final Segment<V> segment;
//    补充描述内容。
    private Mark mark;
    private final Object info;

    public DataBlock(T type, Segment<V> segment, Object info) {
        if (type == null || segment == null) throw new IllegalArgumentException("type or segment can not be null.");
        this.type = type;
        this.segment = segment;
        this.info = info;
    }

    public T getType() {
        return type;
    }

    public Segment<V> getSegment() {
        return segment;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public Object getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return segment.toString();
    }
}
