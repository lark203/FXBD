package com.xx.UI.complex.textArea.content.segment;

import javafx.scene.Node;

import java.io.Serializable;
import java.util.List;

/**
 * 节点占位段 (显示为空格)
 */
public final class NodeSegment<T > extends Segment<T> {
    final NodeInit<T> nodeInit;

    public NodeSegment(T var, NodeInit<T> nodeInit) {
        super(var, _ -> " "); // 固定占位一个空格
        this.nodeInit = nodeInit;
    }
    public void setVar(T var){
        this.var = var;
    }
    public Node getNode() {
        return nodeInit.init(var);
    }

    @Override
    protected void replace(int start, int end, String text, List<Segment<? >> segments) {
        // 直接替换为文本段
        segments.set(segments.indexOf(this), new TextSegment(text));
    }

    @Override
    protected void insertSegment(int index, Segment<? > newSegment, List<Segment<? >> segments) {
        segments.add(segments.indexOf(this) + index, newSegment);
    }

    @Override
    public Segment<T> getSegment(int start, int end) {
        if (start == 0 && end == 1) {
            return this.clone();
        }
        throw new IndexOutOfBoundsException("NodeSegment can only be fully selected (0-1)");
    }

    @Override
    public NodeSegment<T> clone() {
        return new NodeSegment<>(var, nodeInit);
    }

    public interface NodeInit<T >  {
        Node init(T t);
    }
}