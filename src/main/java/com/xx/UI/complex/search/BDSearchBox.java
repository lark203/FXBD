package com.xx.UI.complex.search;

import com.xx.UI.ui.BDControl;
import com.xx.UI.ui.BDSkin;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class BDSearchBox extends BDControl {
    private static final String CSS_CLASS_NAME = "bd-search-box";
    //    存储当前搜索结果的索引
    protected final SimpleIntegerProperty searchBlockIndex = new SimpleIntegerProperty(-1);
    //    触发刷新的变量
    final SimpleBooleanProperty refresh = new SimpleBooleanProperty(false);
    final SimpleStringProperty regularExpression = new SimpleStringProperty();
    final BDSearchPane searchPane;
    private final SimpleBooleanProperty searchCase = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty searchRegex = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty retract = new SimpleBooleanProperty(true);
    //    存储搜索片段，key为行号，value为该行搜索结果,目的是为了cell渲染
    private final Map<Integer, List<SearchResult>> searchResults = new HashMap<>();
    //    存储搜索结果数
    private final SimpleIntegerProperty searchBlockCount = new SimpleIntegerProperty(0);
    //    存储搜索结果，一个搜索结果包含至少一个搜索片段（是否换行）
    private final List<SearchBlock> searchBlocks = new ArrayList<>();
    private final SimpleStringProperty searchText = new SimpleStringProperty();
    private final SimpleBooleanProperty searchSelected = new SimpleBooleanProperty(false);
    private final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);
    // 用于管理搜索任务
    private Task<Void> currentSearchTask;
    private ScheduledFuture<?> scheduledSearch;

    public BDSearchBox(BDSearchPane searchPane) {
        getStyleClass().add(CSS_CLASS_NAME);
        this.searchPane = searchPane;
        mapping.addDisposeEvent(this::dispose);
        searchPane.getMapping().addChildren(getMapping());
    }

    public void refresh() {
        this.refresh.set(!this.refresh.get());
    }

    public boolean isSearchCase() {
        return searchCase.get();
    }

    public void setSearchCase(boolean searchCase) {
        this.searchCase.set(searchCase);
    }

    public SimpleBooleanProperty searchCaseProperty() {
        return searchCase;
    }

    public boolean isSearchRegex() {
        return searchRegex.get();
    }

    public void setSearchRegex(boolean searchRegex) {
        this.searchRegex.set(searchRegex);
    }

    public SimpleBooleanProperty searchRegexProperty() {
        return searchRegex;
    }

    public boolean isRetract() {
        return retract.get();
    }

    public void setRetract(boolean retract) {
        this.retract.set(retract);
    }

    public SimpleBooleanProperty retractProperty() {
        return retract;
    }

    public String getRegularExpression() {
        return regularExpression.get();
    }

    public void setRegularExpression(String regularExpression) {
        this.regularExpression.set(regularExpression);
    }

    public ReadOnlyStringProperty regularExpressionProperty() {
        return regularExpression;
    }

    public String getSearchText() {
        return searchText.get();
    }

    public void setSearchText(String searchText) {
        this.searchText.set(searchText);
    }

    public boolean isSearchSelected() {
        return searchSelected.get();
    }

    public void setSearchSelected(boolean searchSelected) {
        this.searchSelected.set(searchSelected);
    }

    public SimpleBooleanProperty searchSelectedProperty() {
        return searchSelected;
    }

    public SimpleStringProperty searchTextProperty() {
        return searchText;
    }

    public Map<Integer, List<SearchResult>> getSearchResults() {
        return searchResults;
    }

    public List<SearchBlock> getSearchBlocks() {
        return searchBlocks;
    }

    public int getSearchBlockCount() {
        return searchBlockCount.get();
    }

    public ReadOnlyIntegerProperty searchBlockCountProperty() {
        return searchBlockCount;
    }

    public int getSearchBlockIndex() {
        return searchBlockIndex.get();
    }

    public ReadOnlyIntegerProperty searchBlockIndexProperty() {
        return searchBlockIndex;
    }

    public void previousSearchBlock() {
        int index = searchBlockIndex.get();
        if (index > 0) {
            searchBlockIndex.set(index - 1);
        } else searchBlockIndex.set(searchBlockCount.get() - 1);
    }

    public void nextSearchBlock() {
        int index = searchBlockIndex.get();
        if (index < searchBlocks.size() - 1) {
            searchBlockIndex.set(index + 1);
        } else searchBlockIndex.set(0);
    }

    /* 不应该只是单纯的排除，
     * 应该维护一个索引
     * key为查找内容，value为result类
     * result类应该在每次搜索后更新记录
     * result类的数据结构为：
     * 1.匹配内容即searchBlocks（真正的搜索结果）与searchResults（方便cell渲染）
     * 2.记录锚点位置，方便定位
     * 问题：
     * 1.在内容更新后无法更新真正的锚点位置。
     * 2.由于是cell实时更新，必须在外部记录锚点位置，否则无法定位到锚点。
     * 解决：
     * 不考虑复杂搜索功能，即删除排除功能。
     * */
    void exclude(int index) {
//        SearchBlock searchBlock = searchBlocks.get(index);
//        if (searchBlock == null) return;
//        移除对应搜索内容
//        searchBlocks.remove(searchBlock);
//        更新搜索结果数
//        searchBlockCount.set(searchBlocks.size());
//        更新当前搜索结果索引
//        searchBlockIndex.set(Math.min(searchBlockIndex.get(), searchBlocks.size() - 1));
//        更新后续搜索结果索引
//        移除cell渲染内容。
//        searchBlock.getResults().forEach(result -> searchResults.get(result.line).remove(result));
//        更新
//        searchPane.bdSearchResource.updateResult(getSearchBlockIndex(), searchBlocks, searchResults);
    }

    @Override
    protected BDSkin<? extends BDControl> createDefaultSkin() {
        return new BDSearchBoxSkin(this);
    }

    public void triggerSearch(String text, long delayMillis) {
        // 检查是否已关闭
        if (disposed) {
            System.err.println("BDSearchBox is disposed, cannot trigger search");
            return;
        }

        // 取消之前计划的搜索
        if (scheduledSearch != null && !scheduledSearch.isDone()) {
            scheduledSearch.cancel(false);
        }

        // 取消当前正在执行的任务
        if (currentSearchTask != null && !currentSearchTask.isDone()) {
            currentSearchTask.cancel(true);
        }

        // 安排新的搜索任务
        scheduledSearch = scheduler.schedule(() -> {
            if (!disposed) {
                Platform.runLater(() -> search(text));
            }
        }, delayMillis, TimeUnit.MILLISECONDS);
    }
    // 为了方便使用，添加一个立即搜索的方法
    public void triggerSearchImmediate(String text) {
        triggerSearch(text, 0);
    }

    private void search(String text) {
        // 检查是否已关闭
        if (disposed) {
            return;
        }

        // 清空之前的搜索结果
        searchResults.clear();
        searchBlocks.clear();
        // 清空之前的搜索结果（必须在UI线程执行）
        searchResults.clear();
        searchBlocks.clear();
        AtomicInteger oldIndex = new AtomicInteger(getSearchBlockIndex());
        String searchPattern = regularExpression.get();
        if (searchPattern == null || searchPattern.isEmpty() || text == null || text.isEmpty()) {
            searchBlockIndex.set(-1);
            searchBlockCount.set(0);
            return;
        }
//        当搜索类型为搜索选中内容时，需要获取选中内容的起始行和偏移量
        final int startParagraph = isSearchSelected() ? searchPane.bdSearchResource.getSelectedStartParagraph() : 0;
        final int startOffset = isSearchSelected() ? searchPane.bdSearchResource.getSelectedOffset() : 0;

        // 创建后台任务
        currentSearchTask = new Task<>() {
            @Override
            protected Void call() {
                try {
                    int matchCount = 0;
                    int flags = isSearchCase() ? 0 : Pattern.CASE_INSENSITIVE;
                    Pattern pattern = Pattern.compile(searchPattern, flags);
                    Matcher matcher = pattern.matcher(text);
                    Map<Integer, List<SearchResult>> resultsByLine = new HashMap<>();

                    // 预先计算所有行的起始位置（统一处理startOffset）
                    List<Integer> lineStarts = new ArrayList<>();
                    List<Integer> lineLengths = new ArrayList<>();
                    lineStarts.add(0); // 第一行从startOffset开始

                    int lineStart = 0;
                    for (int i = 0; i < text.length(); i++) {
                        if (text.charAt(i) == '\n') {
                            lineLengths.add(i - lineStart);
                            lineStart = i + 1;
                            lineStarts.add(lineStart);
                        }
                    }
                    // 处理最后一行
                    lineLengths.add(text.length() - lineStart);

                    // 在后台线程中收集结果
                    while (matcher.find()&& !disposed) {
                        if (isCancelled()) {
                            break;
                        }

                        int globalStart = matcher.start();
                        int globalEnd = matcher.end();

                        // 使用二分查找确定起始行和结束行
                        int startLine = findLineIndex(lineStarts, globalStart);
                        int endLine = findLineIndex(lineStarts, globalEnd );

                        if (startLine == -1 || endLine == -1) {
                            continue; // 行定位失败，跳过这个匹配
                        }

                        matchCount++;
                        int resultIndex = searchBlocks.size();
                        SearchBlock searchBlock = new SearchBlock(startLine);
                        searchBlocks.add(searchBlock);

                        // 处理匹配（单行或跨行）
                        if (startLine == endLine) {
                            // 单行匹配
                            int lineStartPos = lineStarts.get(startLine);
                            int lineStartOffset = globalStart - lineStartPos+ (startLine == 0 ? startOffset:0);
                            boolean fullLine = (globalStart - lineStartPos == lineLengths.get(startLine));
                            int lineEndOffset =fullLine?lineStartOffset: globalEnd - lineStartPos+ (startLine == 0 ? startOffset:0);

                            int displayLine = startLine + startParagraph;
                            SearchResult result = new SearchResult(displayLine, lineStartOffset,
                                    lineEndOffset, resultIndex, fullLine);
                            resultsByLine.computeIfAbsent(displayLine, _ -> new ArrayList<>()).add(result);
                            searchBlock.addResult(result);
                        } else {
                            // 跨行匹配
                            // 第一部分：开始行
                            int firstLineStartPos = lineStarts.get(startLine);
                            int firstLineStart = globalStart - firstLineStartPos;
                            int firstLineEnd = lineLengths.get(startLine);

                            int displayStartLine = startLine + startParagraph;
                            SearchResult firstResult = new SearchResult(displayStartLine, firstLineStart+ (startLine == 0 ? startOffset:0),
                                    firstLineEnd+ (startLine == 0 ? startOffset:0), resultIndex, true);
                            resultsByLine.computeIfAbsent(displayStartLine, _ -> new ArrayList<>()).add(firstResult);
                            searchBlock.addResult(firstResult);

                            // 中间行
                            for (int line = startLine + 1; line < endLine; line++) {
                                int displayLine = line + startParagraph;
                                SearchResult midResult = new SearchResult(displayLine, 0,
                                        lineLengths.get(line), resultIndex, true);
                                resultsByLine.computeIfAbsent(displayLine, _ -> new ArrayList<>()).add(midResult);
                                searchBlock.addResult(midResult);
                            }

                            // 最后一行
                            int lastLineStartPos = lineStarts.get(endLine);
                            int lastLineStart = 0;
                            int lastLineEnd = globalEnd - lastLineStartPos;

                            int displayEndLine = endLine + startParagraph;
                            SearchResult lastResult = new SearchResult(displayEndLine, lastLineStart,
                                    lastLineEnd, resultIndex, false);
                            resultsByLine.computeIfAbsent(displayEndLine, _ -> new ArrayList<>()).add(lastResult);
                            searchBlock.addResult(lastResult);
                        }
                    }

                    // 在UI线程中更新结果
                    if (!isCancelled() || disposed) {
                        int finalMatchCount = matchCount;
                        searchResults.putAll(resultsByLine);
                        Platform.runLater(() -> {
                            if (oldIndex.get() == -1) oldIndex.set(0);
                            searchBlockCount.set(finalMatchCount);
                            searchBlockIndex.set(Math.min(oldIndex.get(), Math.max(0, searchBlocks.size() - 1)));
                            refresh();
                        });
                    }
                } catch (PatternSyntaxException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        };

        // 启动后台线程
        Thread searchThread = new Thread(currentSearchTask);
        searchThread.setDaemon(true);
        searchThread.start();
    }

    // 使用二分查找优化行定位
    private int findLineIndex(List<Integer> lineStarts, int position) {
        int low = 0;
        int high = lineStarts.size() - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = lineStarts.get(mid);

            if (midVal <= position) {
                if (mid == lineStarts.size() - 1 || lineStarts.get(mid + 1) > position) {
                    return mid;
                }
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    // 添加一个标记，表示是否已关闭
    private volatile boolean disposed = false;

    public void dispose() {
        // 防止重复调用
        if (disposed) {
            return;
        }
        disposed = true;

        // 1. 取消计划的搜索任务
        if (scheduledSearch != null) {
            scheduledSearch.cancel(true); // true表示中断正在执行的任务
        }

        // 2. 取消当前正在执行的搜索任务
        if (currentSearchTask != null && !currentSearchTask.isDone()) {
            currentSearchTask.cancel(true);
        }

        // 3. 关闭线程池
        if (!scheduler.isShutdown()) {
            try {
                // 先尝试优雅关闭
                scheduler.shutdown();

                // 等待一段时间让任务结束
                if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                    // 如果超时，强制关闭
                    List<Runnable> notExecuted = scheduler.shutdownNow();
                    if (!notExecuted.isEmpty()) {
                        System.out.println("BDSearchBox: " + notExecuted.size() +
                                         " tasks were not executed");
                    }
                }
            } catch (InterruptedException e) {
                // 如果等待过程中被中断，强制关闭
                scheduler.shutdownNow();
                Thread.currentThread().interrupt(); // 恢复中断状态
            }
        }

        // 4. 清空数据结构，释放内存
        searchResults.clear();
        searchBlocks.clear();
        searchBlockCount.set(0);
        searchBlockIndex.set(-1);

    }

    public void clearSearch() {
        searchResults.clear();
        searchBlocks.clear();
        searchBlockCount.set(0);
        searchBlockIndex.set(-1);
    }

    //    存储搜索片段。原因：搜索结果可能跨越多行，因此需要存储多个搜索片段。
    public record SearchResult(int line, int startOffset, int endOffset, int resultIndex, boolean fullLine) {
    }

    //    存储搜索片段。这才是真正的搜索结果。
    public static class SearchBlock {
        private final List<SearchResult> results = new ArrayList<>();
        private final int startLine;

        public SearchBlock(int startLine) {
            this.startLine = startLine;
        }

        public int getStartLine() {
            return startLine;
        }

        public void addResult(SearchResult result) {
            results.add(result);
        }

        public List<SearchResult> getResults() {
            return results;
        }

        public boolean contains(SearchResult result) {
            return results.contains(result);
        }

        @Override
        public String toString() {
            return "SearchBlock{" +
                    "results=" + results +
                    '}';
        }
    }
}
