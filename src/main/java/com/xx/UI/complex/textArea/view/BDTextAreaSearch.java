package com.xx.UI.complex.textArea.view;

import com.xx.UI.complex.search.BDSearchBox;
import com.xx.UI.complex.search.BDSearchPane;
import com.xx.UI.complex.search.BDSearchResource;
import com.xx.UI.complex.search.ReplaceAll;
import com.xx.UI.complex.textArea.content.BDTextAreaContent;
import com.xx.UI.util.BDScheduler;
import javafx.beans.property.IntegerProperty;

import java.util.List;
import java.util.Map;

public class BDTextAreaSearch extends BDSearchPane {
    private final BDTextArea area;

    public BDTextAreaSearch(BDTextArea content) {
        area = content;
        super(content);
        BDScheduler scheduler = new BDScheduler(this::refresh, 100);
        BDScheduler scheduler1 = new BDScheduler(()->{int index = area.searchBlockIndex.get();
                    if (index != -1 && searchBox.getSearchBlocks().size() > index) {
                        BDSearchBox.SearchResult last = searchBox.getSearchBlocks().get(index).getResults().getLast();
                        try {
                            area.setCaretPosition(new BDTextAreaContent.Point(last.line(), last.endOffset()));
                        }
                        catch (IndexOutOfBoundsException e){
                            area.setCaretPosition(new BDTextAreaContent.Point(last.line(), last.startOffset()));
                        }
                        area.refresh();
                    }}, 100);
        mapping.addChildren(content.getMapping())
                .addListener(area.selectRangeProperty(),(_,_,nv)->{
                    if (nv.start() == null && isSearchSelect()) {
                        setSearchSelect(false);
                    }
                })
                .addListener(searchSelectProperty(),(_, _, _)-> scheduler.run())
                .addBDScheduler(scheduler)
                .addBDScheduler(scheduler1)
                .addListener(area.searchBlockIndex, (_, _, _) -> scheduler1.run());
        area.addChangeRunnable(scheduler::run);
        getMapping().addChildren(content.getMapping());

    }

    @Override
    protected void replace(BDTextAreaContent.Point start, BDTextAreaContent.Point end, String text) {
        if (start == null || end == null) return;
        area.replace(start, end, text);
    }

    /*
    * 注意：replaceAll调用之后Node节点会被删除，暂时不想写太复杂。
    * */
    @Override
    protected ReplaceAll replaceAll() {
        return (_, newStr, _,b) -> {
            if (b) {
                area.deleteSelect();
                area.insertText(area.getCaretPosition(),newStr);
            }
            else {
                area.delete(0, area.getLength());
                area.insertText(0, newStr);
            }
        };
    }


    @Override
    protected BDSearchResource initialResource(BDSearchPane pane) {
        return new BDSearchResource(pane) {
            @Override
            protected String getResource() {
                return area.toString();
            }

            @Override
            protected String getSelectedResource() {
                return area.getSelectedText();
            }

            @Override
            protected int getSelectedStartParagraph() {
                BDTextArea.SelectRange range = area.getSelectRange();
                if (range == null || range.start() == null) return 0;
                return range.start().paragraph();
            }

            @Override
            protected int getSelectedOffset() {
                BDTextArea.SelectRange range = area.getSelectRange();
                if (range == null || range.start() == null) return 0;
                return range.start().offset();
            }

            @Override
            protected IntegerProperty resultIndexProperty() {
                return area.searchBlockIndex;
            }

            @Override
            protected void updateResult(int searchBlockIndex, List<BDSearchBox.SearchBlock> searchBlocks, Map<Integer, List<BDSearchBox.SearchResult>> resultMap) {
                area.searchResultMap.clear();
                area.searchResultMap.putAll(resultMap);
                area.searchBlocks.clear();
                area.searchBlocks.addAll(searchBlocks);
                area.tempRefresh.set(!area.tempRefresh.get());
            }

        };

    }
}
