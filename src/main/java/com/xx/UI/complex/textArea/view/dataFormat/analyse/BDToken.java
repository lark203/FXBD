package com.xx.UI.complex.textArea.view.dataFormat.analyse;

import java.util.ArrayList;
import java.util.List;

public class BDToken<T extends Enum<?> & Analyse.BDTextEnum<T>> {
    private final  List<Range<T>> Ranges = new ArrayList<>();
    private final int paragraphIndex;

    public BDToken(int paragraphIndex) {
        this.paragraphIndex = paragraphIndex;
    }

    public void addRange(T type, int start, int end,Object info) {
        Ranges.add(new Range<>( start, end,type,info));
    }

    public List<Range<T>> getRanges() {
        return Ranges;
    }

    public int getParagraphIndex() {
        return paragraphIndex;
    }

    public record Range<T extends Enum<?> & Analyse.BDTextEnum<T>>(int start, int end, T type,Object info){}

    @Override
    public String toString() {
        return "BDToken{" +
                "paragraphIndex=" + paragraphIndex +
                ", Ranges=" + Ranges +
                '}';
    }
}
