package com.xx.UI.complex.textArea.view.dataFormat.example.java.parser;

import com.xx.UI.complex.textArea.view.dataFormat.analyse.Token;
import com.xx.UI.complex.textArea.view.dataFormat.example.java.JavaImp.BlockEnumJava;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;


import java.util.List;

public class Java20ErrorListener extends BaseErrorListener {
    private final List<Token<BlockEnumJava>> tokens;

    public Java20ErrorListener(List<Token<BlockEnumJava>> tokens) {
        this.tokens = tokens;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
        tokens.add(Token.transform((org.antlr.v4.runtime.Token) offendingSymbol, BlockEnumJava.ERROR,msg));
    }

    private void underlineError(Recognizer<?,?> recognizer, org.antlr.v4.runtime.Token offendingToken, int line, int charPositionInLine, String msg) {
        CommonTokenStream tokens = (CommonTokenStream) recognizer.getInputStream();
        String input = tokens.getTokenSource().getInputStream().toString();
        String[] lines = input.split("\n");
        String errorLine = lines[line - 1];
        System.err.println(errorLine);
        for (int i = 0; i < charPositionInLine; i++) System.out.print(" ");
        int start = offendingToken.getStartIndex();
        int stop = offendingToken.getStopIndex();
        if (start >= 0 && stop >= 0)
            for (int i = start; i <= stop; i++) System.err.print("^");
        System.err.println();
    }
}
