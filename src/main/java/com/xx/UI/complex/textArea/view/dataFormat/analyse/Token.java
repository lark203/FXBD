package com.xx.UI.complex.textArea.view.dataFormat.analyse;

public record Token<T extends Enum<?> & Analyse.BDTextEnum<T>>(String text, T type, int line, int charPositionInLine,
                                                               Object info) {
    public static <T extends Enum<?> & Analyse.BDTextEnum<T>> Token<T> transform(org.antlr.v4.runtime.Token token, T type, Object info){
        return new Token<>(token.getText(), type, token.getLine() - 1, token.getCharPositionInLine(), info);
    }
}
