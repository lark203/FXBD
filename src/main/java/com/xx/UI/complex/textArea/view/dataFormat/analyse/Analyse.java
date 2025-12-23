package com.xx.UI.complex.textArea.view.dataFormat.analyse;

import com.xx.UI.complex.textArea.content.segment.Paragraph;

import java.util.List;
import java.util.Map;

public interface Analyse<T extends Enum<?> & Analyse.BDTextEnum<T>> {
    /**
     * 给定一个段落的信息，返回该段落的block列表
     */
    List<DataBlock<T, ?>> transform(int paragraphIndex, Paragraph paragraph, BDTokenEntryList<T> tokenEntryList);
    /** 返回该分析器的BDToken列表 */
    List<BDToken<T>> getBDToken(String text);

    /**
     * 给定一个文本，返回该文本的token列表,key为段落索引，value为该段落的token列表。
     * 注意：段落索引从0开始。
     * 此方法的作用是将文本进行分段，然后对每个段落进行分词，并返回每个段落的token列表（即需要高亮的内容）。
     */
    Map<Integer, BDTokenEntryList<T>> transformTokenEntry(String text);

    T getUndefinedType();

    /**
     * 标识一个未定义的类型
     */
    interface BDTextEnum<T extends Enum<?>> {
        T undefinedType();
    }

    /**
     * paragraphBlock用于封装一个段落的block列表 ，目的为缓存每个段落的block列表，避免重复计算
     */
    record ParagraphBlock<T extends Enum<?> & BDTextEnum<T>>(
            int paragraphIndex,
            Paragraph paragraph,
            List<DataBlock<T, ?>> blocks) {
    }
}
