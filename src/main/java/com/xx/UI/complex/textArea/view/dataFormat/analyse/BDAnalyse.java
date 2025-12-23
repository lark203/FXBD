package com.xx.UI.complex.textArea.view.dataFormat.analyse;

import com.xx.UI.complex.textArea.content.BDTextAreaContent;
import com.xx.UI.complex.textArea.content.segment.Paragraph;
import javafx.application.Platform;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BDAnalyse<T extends Enum<?> & Analyse.BDTextEnum<T>> implements Analyse<T> {
    final Map<Integer, List<DataBlock<T, ?>>> dataBlockCacheMap = new ConcurrentHashMap<>();
    final AtomicBoolean processing = new AtomicBoolean(false);
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    Map<Integer, BDTokenEntryList<T>> tokenEntryCacheMap;
    private volatile Future<?> currentTask;
    private Runnable completionCallback;


    @Override
    public final List<DataBlock<T, ?>> transform(
            int paragraphIndex,
            final Paragraph paragraph,
            BDTokenEntryList<T> tokenEntryList) {
        Objects.requireNonNull(paragraph);
        List<DataBlock<T, ?>> list = new ArrayList<>();
        if (tokenEntryList == null || tokenEntryList.getTokenEntries().isEmpty()) {
            paragraph.getSegments()
                    .stream()
                    .map(segment -> new DataBlock<>(getUndefinedType(), segment, null))
                    .forEach(list::add);
        } else
            tokenEntryList.checkTokenEntries(paragraph)
                    .forEach(list::addAll);
        return list;
    }

    @Override
    public final Map<Integer, BDTokenEntryList<T>> transformTokenEntry(String text) {
        Map<Integer, BDTokenEntryList<T>> map = new ConcurrentHashMap<>();
        List<BDToken<T>> tokens = getBDToken(text);

        for (BDToken<T> token : tokens) {
            final int paraIndex = token.getParagraphIndex();

            // 每个段落只查找/创建一次 EntryList
            BDTokenEntryList<T> entryList = map.computeIfAbsent(paraIndex, _ -> new BDTokenEntryList<>(getUndefinedType()));

            // 预分配空间减少扩容开销
            List<BDToken.Range<T>> ranges = token.getRanges();
            if (!ranges.isEmpty()) {
                for (BDToken.Range<T> range : ranges) {
                    // 直接复用 Range 对象创建 TokenEntry
                    entryList.addTokenEntry(new BDTokenEntry<>(
                            range.start(),
                            range.end(),
                            range.type(),
                            range.info()
                    ));
                }
            }
        }
        return map;
    }

public boolean isTaskRunning() {
    return currentTask != null && !currentTask.isDone();
}

public boolean wasLastTaskCancelled() {
    return currentCancellationFlag.get();
}
    // 添加取消标志和锁
    private final Object taskLock = new Object();
    private volatile AtomicBoolean currentCancellationFlag = new AtomicBoolean(false);

    public void setTextAsync(String text, Runnable onComplete) {
        // 创建新的取消标志
        AtomicBoolean cancellationFlag = new AtomicBoolean(true);

        synchronized (taskLock) {
            // 取消当前任务
            if (currentTask != null) {
                currentCancellationFlag.set(true); // 标记旧任务取消
                currentTask.cancel(true); // 尝试中断线程
            }

            // 更新当前取消标志
            currentCancellationFlag = cancellationFlag;
            this.completionCallback = onComplete != null ? onComplete : () -> {};
        }


        // 提交新任务
        currentTask = executor.submit(() -> {
            // 检查任务是否已被取消
            if (cancellationFlag.get())
                return;

            try {
                Map<Integer, BDTokenEntryList<T>> tempTokenEntryMap = transformTokenEntry(text);

                // 再次检查取消状态
                if (cancellationFlag.get())
                    return;

                // 安全更新共享状态
                synchronized (taskLock) {
                    if (tokenEntryCacheMap != null) tokenEntryCacheMap.clear();
                    processing.set(false);
                    dataBlockCacheMap.clear();
                    tokenEntryCacheMap = tempTokenEntryMap;
                }

                // 安全更新UI
                Platform.runLater(() -> {
                    if (!cancellationFlag.get())
                        completionCallback.run();
                });
            } catch (CancellationException _) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 重置取消标志（任务开始后允许执行）
        cancellationFlag.set(false);
    }

    // 检查任务状态
    public boolean isProcessing() {
        return processing.get();
    }

    public void setProcessing(boolean processing) {
        this.processing.set(processing);
    }

    // 关闭线程池（在不再需要时调用）
    public void shutdown() {
        executor.shutdownNow();
    }

    public Map<Integer, BDTokenEntryList<T>> getTokenEntryCacheMap() {
        return tokenEntryCacheMap;
    }

    public List<DataBlock<T, ?>> getDataBlock(int paragraphIndex, Paragraph paragraph) {
        return dataBlockCacheMap
                .computeIfAbsent(paragraphIndex, _ ->
                        transform(paragraphIndex, paragraph, tokenEntryCacheMap.get(paragraphIndex)));
    }

    void append(int paragraphIndex, int offset, List<Paragraph> paragraphs) {
        if (paragraphs == null || paragraphs.isEmpty()) throw new IllegalArgumentException("变化的段落不能为空");
        T type = getUndefinedType();
        if (tokenEntryCacheMap.containsKey(paragraphIndex)) {
            T type1 = tokenEntryCacheMap.get(paragraphIndex).getType(offset);
            type = type1 == null ? type : type1;
        }
        if (tokenEntryCacheMap.containsKey(paragraphIndex)) {
            BDTokenEntryList<T> entryList = tokenEntryCacheMap.get(paragraphIndex);
            entryList.addTokenEntry(offset, paragraphs.getFirst(), getUndefinedType());
        }
        if (paragraphs.size() > 1) {
            addParagraphIndex(paragraphIndex + 1, paragraphs.size() - 1);
            if (tokenEntryCacheMap.containsKey(paragraphIndex)) {
                BDTokenEntryList<T> entryList = tokenEntryCacheMap.get(paragraphIndex);
                BDTokenEntryList<T> remove = entryList.remove(offset);
                entryList.addTokenEntry(entryList.getTokenEntries().isEmpty() ? 0 : entryList.getTokenEntries().getLast().getEnd(), paragraphs.getFirst(), type);
                remove.addTokenEntry(0, paragraphs.getLast(), type);
                tokenEntryCacheMap.put(paragraphIndex + paragraphs.size() - 1, remove);
            }
            for (int i = 1; i < paragraphs.size(); i++) {
                BDTokenEntryList<T> value = new BDTokenEntryList<>(getUndefinedType());
                int length = paragraphs.get(i).getLength();
                if (length <= 0) continue;
                value.addTokenEntry(new BDTokenEntry<>(0, length, type, null));
                tokenEntryCacheMap.put(paragraphIndex + i, value);
            }
        }
        dataBlockCacheMap.clear();
    }

    private void addParagraphIndex(int startParagraphIndex, int line) {
        if (line < 1) return;
        // 获取需要移动的键（已排序）
        List<Integer> keys = new ArrayList<>(tokenEntryCacheMap.keySet());
        Collections.sort(keys);

        // 从大到小处理键值，避免覆盖
        for (int i = keys.size() - 1; i >= 0; i--) {
            int key = keys.get(i);
            if (key >= startParagraphIndex) {
                // 直接修改键值（避免创建新条目）
                BDTokenEntryList<T> value = tokenEntryCacheMap.remove(key);
                if (value == null) continue;
                tokenEntryCacheMap.put(key + line, value);
            }
        }
    }

    void delete(int startParagraphIndex, int startOffset, int endParagraphIndex, int endOffset, List<Paragraph> paragraphs) {
        if (paragraphs == null || paragraphs.isEmpty()) throw new IllegalArgumentException("变化的段落不能为空");
        if (paragraphs.size() == 1) {
            if (tokenEntryCacheMap.containsKey(startParagraphIndex))
                tokenEntryCacheMap.get(startParagraphIndex).remove(startOffset, endOffset);
        } else {
            if (tokenEntryCacheMap.containsKey(startParagraphIndex)) {
                BDTokenEntryList<T> startEntryList = tokenEntryCacheMap.get(startParagraphIndex);
                startEntryList.remove(startOffset);
                if (tokenEntryCacheMap.containsKey(endParagraphIndex)) {
                    tokenEntryCacheMap.get(endParagraphIndex).remove(0, endOffset);
                    tokenEntryCacheMap.get(endParagraphIndex).getTokenEntries().forEach(tokenEntry -> startEntryList.addTokenEntry(new BDTokenEntry<>(startOffset + tokenEntry.getStart(), startOffset + tokenEntry.getEnd(), tokenEntry.getType(), tokenEntry.getInfo())));
                }
            }
            for (int i = 1; i < paragraphs.size(); i++)
                tokenEntryCacheMap.remove(startParagraphIndex + i);
            moveUpParagraphIndex(startParagraphIndex + 1, paragraphs.size() - 1);
        }
        dataBlockCacheMap.clear();
    }

    private void moveUpParagraphIndex(int startParagraphIndex, int line) {
        if (line <= 0) return; // 无效移动行数直接返回

        // 获取需要移动的键（已排序）
        List<Integer> keys = new ArrayList<>(tokenEntryCacheMap.keySet());
        Collections.sort(keys); // 升序排列

        // 从小到大处理键值，避免覆盖
        for (int key : keys) {
            if (key >= startParagraphIndex) {
                // 计算新键值（上移）
                int newKey = key - line;

                // 检查新键值有效性
                if (newKey < 0) {
                    throw new IllegalArgumentException("移动导致段落索引变为负数: " + newKey);
                }

                // 先移除旧键值对
                BDTokenEntryList<T> value = tokenEntryCacheMap.remove(key);

                // 检查目标位置是否被占用（仅限小于startParagraphIndex的键）
                if (tokenEntryCacheMap.containsKey(newKey) && newKey < startParagraphIndex) {
                    throw new IllegalStateException("目标位置已被未移动段落占用: " + newKey);
                }

                // 放入新位置
                tokenEntryCacheMap.put(newKey, value);
            }
        }
    }

    public DataBlockEntry<T> getDataBlockEntry(BDTextAreaContent.Point point) {
        if (dataBlockCacheMap.containsKey(point.paragraph())) {
            List<DataBlock<T, ?>> dataBlocks = dataBlockCacheMap.get(point.paragraph());
            int tempLen = 0;

            for (int i = 0; i < dataBlocks.size(); i++) {
                DataBlock<T, ?> dataBlock = dataBlocks.get(i);
                int blockLength = dataBlock.getSegment().getLength();

                // 情况1：偏移量正好在块边界（当前块起始位置）
                if (tempLen == point.offset()) {
                    return new DataBlockEntry<>(i > 0 ? dataBlocks.get(i - 1) : null, dataBlock);
                }

                // 情况2：偏移量在块结束位置
                if (tempLen + blockLength == point.offset()) {
                    return new DataBlockEntry<>(dataBlock,
                            (i < dataBlocks.size() - 1) ? dataBlocks.get(i + 1) : null);
                }

                // 情况3：偏移量在块内部
                if (tempLen + blockLength > point.offset()) {
                    return new DataBlockEntry<>(dataBlock, dataBlock);
                }

                tempLen += blockLength;
            }
        }
        return null;
    }

    public record DataBlockEntry<T extends Enum<?> & Analyse.BDTextEnum<T>>(DataBlock<T, ?> left,
                                                                            DataBlock<T, ?> right) {
    }
}
