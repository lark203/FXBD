package com.xx.UI.complex.textArea.content.segment;

import com.xx.UI.util.LazyValue;

import java.io.Serializable;
import java.util.List;

/**
 * 存储的最小单元基类
 */
public abstract class Segment<T> implements  Cloneable {
    private final InitInfo<T> init; // 不可变初始化逻辑
    protected T var;
    public Segment(T var, InitInfo<T> init) {
        this.var = var;
        this.init = init;
    }

    public final String getInfo() {
        return init.formatInfo(var);
    }
    public final Integer getLength() {
        return getInfo().length();
    }

    protected abstract void replace(int start, int end, String text, List<Segment<? >> segments);

    public final T getVar() {
        return var;
    }

    protected abstract void insertSegment(int index, Segment<? > newSegment, List<Segment<? >> segments);

    /**
     * 获取指定范围的子段
     *
     * @param start 起始位置（包含）
     * @param end   结束位置（不包含）
     * @return 子段
     */
    public abstract Segment<?> getSegment(int start, int end);

    @Override
    abstract public Segment<T> clone();

    @Override
    public String toString() {
        return getInfo();
    }

    public interface InitInfo<T> {
        String formatInfo(T t);
    }

}