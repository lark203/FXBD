package com.xx.UI.complex.textArea.content.segment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 段落容器 (管理多个内容段)
 */
public final class Paragraph implements Serializable, Cloneable {
    private final List<Segment<? >> segments = new ArrayList<>();
    private final StringBuilder fullContent = new StringBuilder(); // 文本缓存

    public Paragraph() {
    }

    /**
     * 添加文本内容 (自动合并相邻文本段)
     */
    public void appendString(String s) {
        Objects.requireNonNull(s, "Text cannot be null");
        fullContent.append(s);
        if (segments.isEmpty()) {
            segments.add(new TextSegment(s));
            return;
        }
        Segment<?> last = segments.getLast();
        if (last instanceof TextSegment) {
            ((TextSegment) last).getVar().append(s);
        } else {
            segments.add(new TextSegment(s));
        }
        refreshGraphics(segments);
    }

    /**
     * 在指定索引位置插入字符串
     */
    public void insertString(int index, String text) {
        Objects.requireNonNull(text, "Text cannot be null");
        final int length = getLength();

        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException(
                    String.format("Invalid index %d in paragraph of length %d", index, length));
        }

        // 处理边界情况（尾部插入）
        if (index == length) {
            appendString(text);
            return;
        }
        fullContent.insert(index, text);
        int currentPos = 0;
        for (int i = 0; i < segments.size(); i++) {
            Segment<?> seg = segments.get(i);
            final int segLength = seg.getLength();
            final int segEnd = currentPos + segLength;

            if (index < segEnd) {
                final int segOffset = index - currentPos;

                if (seg instanceof TextSegment textSeg) {
                    // 在文本段中间插入：拆分并插入新文本
                    StringBuilder sb = textSeg.getVar();
                    String firstPart = sb.substring(0, segOffset);
                    String secondPart = sb.substring(segOffset);

                    segments.set(i, new TextSegment(firstPart + text));
                    segments.add(i + 1, new TextSegment(secondPart));
                } else {
                    // 在节点段前/后插入
                    if (segOffset == 0) {
                        segments.add(i, new TextSegment(text));
                    } else {
                        segments.add(i + 1, new TextSegment(text));
                    }
                }
                refreshGraphics(segments);
                return;
            }
            currentPos = segEnd;
        }
        refreshGraphics(segments);
    }

    /**
     * 在指定范围插入新段
     */
    public void insertSegment(int index, Segment<? > newSegment) {
        Objects.requireNonNull(newSegment, "New segment cannot be null");
        if (index < 0 || index > getLength()) throw new IndexOutOfBoundsException();
        fullContent.insert(index, newSegment.getInfo());
        int currentPos = 0;
        if (segments.isEmpty()) {
            segments.add(newSegment);
            refreshGraphics(segments);
            return;
        }
        for (Segment<? > segment : segments) {
            final int segLength = segment.getLength();
            if (index == currentPos) {
                segments.add(segments.indexOf(segment), newSegment);
                break;
            } else if (index == currentPos + segLength) {
                segments.add(segments.indexOf(segment) + 1, newSegment);
                break;
            } else if (index < currentPos + segLength) {
                segment.insertSegment(index - currentPos, newSegment, segments);
                break;
            }
            currentPos += segLength;
        }
        refreshGraphics(segments);
    }

    public void insertSegment(int start, int end, Segment<? > newSegment) {
        Objects.requireNonNull(newSegment, "New segment cannot be null");
        if (start < 0 || end > getLength() || start > end) throw new IndexOutOfBoundsException();
        remove(start, end);
        insertSegment(start, newSegment);
    }

    public void insertParagraph(int index, Paragraph paragraph) {
        Objects.requireNonNull(paragraph, "Paragraph cannot be null");
        if (index < 0 || index > getLength()) throw new IndexOutOfBoundsException();
        AtomicInteger currentPos = new AtomicInteger();
        paragraph.segments.forEach(segment -> {
            insertSegment(index + currentPos.get(), segment);
            currentPos.addAndGet(segment.getLength());
        });
        refreshGraphics(segments);
    }

    /**
     * 替换指定范围的文本
     */
    public void replaceString(int start, int end, String text) {
        Objects.requireNonNull(text, "Replacement text cannot be null");
        final int length = getLength();
        if (start < 0 || end > length || start > end) {
            throw new IndexOutOfBoundsException(
                    String.format("Invalid Range [%d, %d] in paragraph of length %d", start, end, length));
        }
        if (start == end) {// 零长度操作
            insertString(start, text);
            return;
        }

        fullContent.replace(start, end, text);
        List<Segment<?>> changedSegments = new ArrayList<>();
        int currentPos = 0;
        int currentLength = 0;
        for (Segment<? > segment : segments) {
            if (end <= currentPos) break;
            final int segLength = segment.getLength();
            currentPos += segLength;
            if (start < currentPos) changedSegments.add(segment);
            else currentLength += segLength;
        }
        Segment<?> first = changedSegments.getFirst();
        if (changedSegments.size() == 1) first.replace(start - currentLength, end - currentLength, text, segments);
        else {
            int firstLen = first.getLength();
            Segment<?> last = changedSegments.getLast();
            int lastIndex = segments.indexOf(last);
            if (changedSegments.size() == 2) {
                first.replace(start - currentLength, firstLen, "", segments);
                last.replace(0, end - currentLength - firstLen, "", segments);
            } else {
                int temp = currentLength + firstLen;
                first.replace(start - currentLength, firstLen, "", segments);
                for (int i = 1; i < changedSegments.size() - 1; i++) {
                    temp += changedSegments.get(i).getLength();
                    changedSegments.get(i).replace(0, changedSegments.get(i).getLength(), "", segments);
                }
                last.replace(0, end - temp, "", segments);
            }
            segments.add(lastIndex, new TextSegment(text));
        }
        refreshGraphics(segments);
    }

    /**
     * 移除指定范围的段
     */
    public void remove(int start, int end) {
        replaceString(start, end, "");
        refreshGraphics(segments);
    }

    /**
     * 合并相邻文本段
     */
    public static void refreshGraphics(List<Segment<?>> segments) {
        clearEmptySegment(segments);
        if (segments.size() < 2) return;

        Iterator<Segment<?>> it = segments.iterator();
        Segment<?> prev = it.next();

        while (it.hasNext()) {
            Segment<?> curr = it.next();
            if (prev instanceof TextSegment && curr instanceof TextSegment) {
                // 合并文本段
                ((TextSegment) prev).getVar().append(((TextSegment) curr).getVar());
                it.remove();
            } else {
                prev = curr;
            }
        }
    }
    public static void clearEmptySegment(List<Segment<?>> segments){
        segments.removeIf(segment -> segment.getLength() == 0);
    }


    public List<Segment<? >> getSegments() {
        return segments.stream().toList(); // 防御性拷贝
    }

    public String getText(int start, int end) {
        if (start < 0 || end > getLength() || start > end) throw new IndexOutOfBoundsException();
        return toString().substring(start, end);
    }

    /**
     * 获取指定范围的段落
     *
     * @param start 起始位置（包含）
     * @param end   结束位置（不包含）
     * @return 新段落
     */
    public Paragraph getParagraph(int start, int end) {
        final int length = getLength();
        if (start < 0 || end > length || start > end) {
            throw new IndexOutOfBoundsException(
                    String.format("Invalid Range [%d, %d] in paragraph of length %d", start, end, length));
        }

        Paragraph newPara = new Paragraph();
        int currentPos = 0;
        for (Segment<? > seg : segments) {
            if (currentPos >= end) break;

            final int segLength = seg.getLength();
            final int segEnd = currentPos + segLength;

            if (segEnd <= start) {
                currentPos = segEnd;
                continue;
            }

            final int segStart = Math.max(start - currentPos, 0);
            final int segEndInRange = Math.min(end - currentPos, segLength);

            if (segStart < segEndInRange) {
                Segment<?> subSeg = seg.getSegment(segStart, segEndInRange);
                newPara.appendSegment(subSeg);
            }
            currentPos = segEnd;
        }
        refreshGraphics(segments);
        newPara.fullContent.setLength(0);
        newPara.fullContent.append(this.fullContent, start, end);
        refreshGraphics(newPara.segments);
        return newPara;
    }

    /**
     * 直接添加段对象（不合并）
     */
    private void appendSegment(Segment<? > segment) {
        segments.add(segment);
    }

    @Override
    public String toString() {
        return fullContent.toString();
    }

    public int getLength() {
        return fullContent.length();
    }

    @Override
    public Paragraph clone() {
        Paragraph paragraph = new Paragraph();
        paragraph.segments.addAll(this.segments.stream().map(Segment::clone).toList());
        paragraph.fullContent.append(this.fullContent);
        return paragraph;
    }
}