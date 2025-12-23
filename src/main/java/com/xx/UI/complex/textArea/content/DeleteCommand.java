package com.xx.UI.complex.textArea.content;

import com.xx.UI.complex.textArea.content.segment.Paragraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeleteCommand extends ContentCommand {
    final List<Paragraph> deletedParagraphs = new ArrayList<>();
    int start;
    int end;
    BDTextAreaContent.Point tempPoint;

    protected DeleteCommand(int start, int end, BDTextAreaContent.Point tempPoint, boolean fireEvent, BDTextAreaContent content) {
        super(content.locatePosition(start), content, fireEvent);
        Objects.requireNonNull(tempPoint);
        this.start = start;
        this.end = end;
        endCaret = getEndPoint();
        if (tempPoint.equals(content.getStart()) || tempPoint.equals(content.getEnd()) || content.getEnd() == null)
            this.tempPoint = tempPoint;
        else
            this.tempPoint = content.getEnd();
    }

    @Override
    public void execute() {
        deletedParagraphs.clear();
        deletedParagraphs.addAll(content.deleteAction(start, end, fireEvent));
    }

    @Override
    public void unexecute() {
        content.insertParagraphs(start, deletedParagraphs, fireEvent);
        content.setCaretPosition(tempPoint);
        content.setSelectRange(startCaret, endCaret);
    }

    @Override
    public boolean merge(Command command) {
        if (command instanceof DeleteCommand deleteCommand) {
            /* 保证只合并删除段落为一个段落的情况 */
            if (deleteCommand.deletedParagraphs.size() == 1
                    && deleteCommand.startCaret.paragraph() == startCaret.paragraph()) {
                if (deleteCommand.start == start - 1) {
                    deletedParagraphs.getFirst().insertParagraph(0, deleteCommand.deletedParagraphs.getFirst());
                    start -= (deleteCommand.end - deleteCommand.start);
                    startCaret = content.locatePosition(start);
                    return true;
                }
                if (deleteCommand.start == start) {
                    deletedParagraphs.getLast().insertParagraph(deletedParagraphs.getLast().getLength(), deleteCommand.deletedParagraphs.getFirst());
                    end += (deleteCommand.end - deleteCommand.start);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    BDTextAreaContent.Point getEndPoint() {
        return content.locatePosition(end);
    }
}
