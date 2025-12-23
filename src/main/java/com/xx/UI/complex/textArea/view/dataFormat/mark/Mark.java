package com.xx.UI.complex.textArea.view.dataFormat.mark;

import com.xx.UI.complex.textArea.view.dataFormat.analyse.Analyse;
import com.xx.UI.complex.textArea.view.dataFormat.analyse.DataBlock;

import java.util.Objects;

/**
 * 此类是用来补充dataBlock信息的标记类，只能在BDTextInitFactory类中的splitDataBlocks中的dataBlock进行设置。
 */
public class Mark {
    private DataBlock<? extends Analyse.BDTextEnum<? extends Enum<?>>, ? > dataBlock;
    private MarkNode node;
    private MARK_DIRECTION direction;

    public Mark(
            DataBlock<? extends Analyse.BDTextEnum<? extends Enum<?>>, ? > dataBlock,
            MarkNode node,
            MARK_DIRECTION direction) {
        setDataBlock(dataBlock);
        setNode(node);
        setDirection(direction);
    }

    public DataBlock<? extends Analyse.BDTextEnum<? extends Enum<?>>, ? > getDataBlock() {
        return dataBlock;
    }

    public void setDataBlock(DataBlock<? extends Analyse.BDTextEnum<? extends Enum<?>>, ? > dataBlock) {
        this.dataBlock = Objects.requireNonNull(dataBlock, "DataBlock cannot be null");
    }

    public MarkNode getNode() {
        return node;
    }

    public void setNode(MarkNode node) {
        if (this.node != null)
            this.node.setMark(null);
        this.node = Objects.requireNonNull(node, "Node cannot be null");
        node.setMark(this);
    }

    public MARK_DIRECTION getDirection() {
        return direction;
    }

    public void setDirection(MARK_DIRECTION direction) {
        this.direction = Objects.requireNonNull(direction, "Direction cannot be null");
    }
}