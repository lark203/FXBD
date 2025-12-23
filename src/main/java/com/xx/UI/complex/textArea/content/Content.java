package com.xx.UI.complex.textArea.content;

import com.xx.UI.complex.textArea.content.segment.NodeSegment;

public interface Content {

    /**
     * 过滤输入内容，去除不合法的字符，并返回过滤后的内容。
     *
     * @param var0 输入内容
     * @param var1 是否过滤换行符
     * @param var2 是否过滤制表符
     * @return 过滤后的内容
     *
     */
    static String filterInput(String var0, boolean var1, boolean var2) {
        if (containsInvalidCharacters(var0, var1, var2)) {
            StringBuilder var3 = new StringBuilder(var0.length());

            for (int var4 = 0; var4 < var0.length(); ++var4) {
                char var5 = var0.charAt(var4);
                if (!isInvalidCharacter(var5, var1, var2)) {
                    var3.append(var5);
                }
            }

            var0 = var3.toString();
        }

        return var0;
    }

    static boolean containsInvalidCharacters(String var0, boolean var1, boolean var2) {
        for (int var3 = 0; var3 < var0.length(); ++var3) {
            char var4 = var0.charAt(var3);
            if (isInvalidCharacter(var4, var1, var2)) {
                return true;
            }
        }

        return false;
    }

    static boolean isInvalidCharacter(char var0, boolean var1, boolean var2) {
        if (var0 == 127) {
            return true;
        } else if (var0 == '\n') {
            return var1;
        } else if (var0 == '\t') {
            return var2;
        } else {
            return var0 < ' ';
        }
    }

    String get(int start, int end);

    void insert(int index, String text, boolean fireEvent);

    <T> void insert(int index, NodeSegment<T> segment, boolean fireEvent);

    <T> void replace(int start, int end, NodeSegment<T> segment, boolean fireEvent);

    void replace(int start, int end, String text, boolean fireEvent);

    void delete(int start, int end, boolean fireEvent);

    int length();
}
