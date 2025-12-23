package com.xx.UI.complex.textArea.view.dataFormat.analyse;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unchecked")
public class RegexAnalyse<T extends Enum<?> & Analyse.BDTextEnum<T>> extends BDAnalyse<T> {
    private final T undefinedType;
    private final List<T> orderedTypes = new ArrayList<>(20);
    // 使用更高效的存储：EnumMap 替代 HashMap
    private Map<T, List<Pattern>> regexRules;

    public RegexAnalyse(String text, T undefinedType) {
        super();
        this.undefinedType = undefinedType;
        regexRules = new EnumMap<>(undefinedType.getClass());
    }

    // ===== 核心方法：添加正则规则 =====
    public void addRegexRule(T type, String regex, boolean multiLineMode) {
        int flags = multiLineMode ? Pattern.MULTILINE | Pattern.DOTALL : 0;
        Pattern pattern = Pattern.compile(regex, flags);
        addRegexRule(type, pattern);
    }

    public void addRegexRule(T type, Pattern pattern) {
        if (regexRules == null) {
            regexRules = new EnumMap<>(undefinedType.getClass());
        }
        if (!orderedTypes.contains(type))
            orderedTypes.add(type);
        regexRules.computeIfAbsent(type, _ -> new ArrayList<>(4)).add(pattern);
    }

    // ===== 便捷方法：添加关键字规则 =====
    public void addKeyword(T type, String keyword, boolean multiLineMode) {
        addRegexRule(type, "\\b" + Pattern.quote(keyword) + "\\b", multiLineMode);
    }

    public void addKeywords(T type, Collection<String> keywords, boolean multiLineMode) {
        if (keywords == null || keywords.isEmpty()) return;
        addRegexRule(type, buildBoundedKeywordRegex(keywords), multiLineMode);
    }

    public void addKeyword(T type, String keyword, boolean multiLineMode, boolean requireBoundary) {
        String escaped = Pattern.quote(keyword);
        addRegexRule(type, requireBoundary ? "\\b" + escaped + "\\b" : escaped, multiLineMode);
    }

    public void addKeywords(T type, Collection<String> keywords, boolean multiLineMode, boolean requireBoundary) {
        if (keywords == null || keywords.isEmpty()) return;
        String regex = requireBoundary ?
                buildBoundedKeywordRegex(keywords) :
                buildUnboundedKeywordRegex(keywords);
        addRegexRule(type, regex, multiLineMode);
    }

    // ===== 辅助方法：构建关键字正则 =====
    private String buildBoundedKeywordRegex(Collection<String> keywords) {
        return "\\b(?:" + String.join("|", keywords.stream().map(Pattern::quote).toList()) + ")\\b";
    }

    private String buildUnboundedKeywordRegex(Collection<String> keywords) {
        return "(?:" + String.join("|", keywords.stream().map(Pattern::quote).toList()) + ")";
    }

    // ===== 核心分析逻辑 - 性能优化版本 =====
    @Override
    public List<BDToken<T>> getBDToken(String text) {
        if (text == null || text.isEmpty() || regexRules == null || regexRules.isEmpty()) {
            return Collections.emptyList();
        }

        // 1. 预处理：构建行首索引列表（优化版）
        List<Integer> lineStarts = new ArrayList<>();
        List<Integer> lineEnds = new ArrayList<>();
        lineStarts.add(0);

        int pos = 0;
        int textLength = text.length();
        while (pos < textLength) {
            int newLinePos = text.indexOf('\n', pos);
            if (newLinePos == -1) break;

            lineEnds.add(newLinePos);
            pos = newLinePos + 1;
            lineStarts.add(pos);
        }

        // 处理最后一行
        if (lineStarts.size() > lineEnds.size()) {
            lineEnds.add(textLength);
        }

        Map<Integer, BDToken<T>> tokenMap = new HashMap<>(lineStarts.size() * 2);

        // 2. 优化正则匹配：减少对象创建和循环嵌套
        for (T type : orderedTypes) {
            List<Pattern> patterns = regexRules.get(type);
            if (patterns == null) continue;

            for (Pattern pattern : patterns) {
                Matcher matcher = pattern.matcher(text);
                while (matcher.find()) {
                    processMatch(matcher, type, lineStarts, lineEnds, tokenMap);
                }
            }
        }

        return new ArrayList<>(tokenMap.values());
    }

    // 处理单个匹配项（提取为独立方法减少嵌套）
    private void processMatch(Matcher matcher, T type,
                              List<Integer> lineStarts, List<Integer> lineEnds,
                              Map<Integer, BDToken<T>> tokenMap) {
        int start = matcher.start();
        String matchedText = matcher.group();
        int textLength = matcher.hitEnd() ? matcher.end() : matcher.end() - 1;

        // 3. 高效行号查找（优化二分查找）
        int lineIdx = binarySearch(lineStarts, start);
        int lineNumber;
        int lineStartIndex;

        if (lineIdx >= 0) {
            lineNumber = lineIdx;
            lineStartIndex = lineStarts.get(lineNumber);
        } else {
            lineNumber = -lineIdx - 2;
            lineStartIndex = lineStarts.get(lineNumber);
        }

        // 4. 处理匹配文本（避免split创建临时数组）
        int currentLine = lineNumber;
        int currentPos = start;
        int segmentStart = start - lineStartIndex;
        int matchedLength = matchedText.length();
        int processed = 0;

        while (processed < matchedLength) {
            // 5. 获取当前行边界
            int lineEnd = (currentLine < lineEnds.size()) ?
                    lineEnds.get(currentLine) : textLength;
            int lineStart = lineStarts.get(currentLine);
            int lineLength = lineEnd - lineStart;

            // 6. 计算当前行内的段长度
            int segmentEndInLine = Math.min(
                    segmentStart + (matchedLength - processed),
                    lineLength
            );
            int segmentLength = segmentEndInLine - segmentStart;

            // 7. 跳过空段（优化点）
            if (segmentLength > 0) {
                int finalCurrentLine = currentLine;
                tokenMap.computeIfAbsent(currentLine, k -> new BDToken<>(finalCurrentLine))
                        .addRange(type, segmentStart, segmentStart + segmentLength, null);
            }

            // 8. 移动到下一行
            processed += segmentLength;
            currentLine++;
            currentPos += segmentLength;

            // 9. 重置下一行的起始位置
            segmentStart = 0;

            // 10. 跳过换行符
            if (currentPos < textLength && textLength > 0) {
                char c = matcher.hitEnd() ? '\0' : matcher.group().charAt(processed);
                if (c == '\n' || c == '\r') {
                    processed++;
                    currentPos++;
                }
            }
        }
    }

    // 优化的二分查找实现
    private int binarySearch(List<Integer> list, int key) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = list.get(mid);

            if (midVal < key) {
                low = mid + 1;
            } else if (midVal > key) {
                high = mid - 1;
            } else {
                return mid; // 精确匹配
            }
        }
        return -low - 1; // 返回插入点
    }

    // ===== 其他方法保持不变 =====
    @Override
    public T getUndefinedType() {
        return undefinedType;
    }

    public Set<T> getRegisteredTypes() {
        return regexRules != null ? regexRules.keySet() : Collections.emptySet();
    }

    public void clearRulesForType(T type) {
        if (regexRules != null) {
            regexRules.remove(type);
        }
    }

    public void clearAllRules() {
        if (regexRules != null) {
            regexRules.clear();
        }
    }
}