package com.xx.UI.complex.textArea.content;

import com.xx.UI.complex.textArea.content.segment.NodeSegment;

import java.io.Serializable;

import static com.xx.UI.util.Util.countNewlines;

public class InsertCommand extends ContentCommand {
    final int index;
    String text;
    NodeSegment<? > segment;

    public InsertCommand(int index, String text, boolean fireEvent, BDTextAreaContent content) {
        super(content.locatePosition(index), content, fireEvent);
        this.index = index;
        this.text = text;
        endCaret = getEndPoint();
    }

    public InsertCommand(int index, NodeSegment<? > segment, boolean fireEvent, BDTextAreaContent content) {
        super(content.locatePosition(index), content, fireEvent);
        this.index = index;
        this.segment = segment;
        endCaret = getEndPoint();
    }


    @Override
    public void execute() {
        if (text != null) content.insertAction(index, text, fireEvent);
        else if (segment != null) content.insertAction(index, segment, fireEvent);
    }

    @Override
    public void unexecute() {
        if (text != null) content.deleteAction(index, index + text.length(), true);
        else if (segment != null) content.deleteAction(index, index + segment.getLength(), true);
    }

    @Override
    public boolean merge(Command command) {
        if (text != null && !text.endsWith("\n")) {
            if (command instanceof InsertCommand insertCommand
                    && insertCommand.text != null
                    && !insertCommand.text.startsWith("\n")
                    && insertCommand.startCaret.paragraph() == endCaret.paragraph()
                    && insertCommand.startCaret.offset() == endCaret.offset()) {
                text = insertCommand.text + text;
                endCaret = insertCommand.endCaret;
                return true;
            }
        }
        return false;
    }

    @Override
    BDTextAreaContent.Point getEndPoint() {
        if (text == null)
            return new BDTextAreaContent.Point(startCaret.paragraph(), startCaret.offset() + segment.getLength());

        int i = countNewlines(text);
        if (i == 0)
            return new BDTextAreaContent.Point(startCaret.paragraph(), startCaret.offset() + text.length());

        // 计算最后一个换行符的位置
        int lastNewlineIndex = text.lastIndexOf('\n');
        // 最后一行长度 = 总长度 - (最后一个换行符位置 + 1)
        int lastLineLength = text.length() - (lastNewlineIndex + 1);
        return new BDTextAreaContent.Point(startCaret.paragraph() + i, lastLineLength);
    }
}
