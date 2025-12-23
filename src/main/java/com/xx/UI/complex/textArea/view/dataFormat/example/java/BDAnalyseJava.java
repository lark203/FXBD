package com.xx.UI.complex.textArea.view.dataFormat.example.java;

import com.xx.UI.complex.textArea.view.dataFormat.analyse.BDAnalyse;
import com.xx.UI.complex.textArea.view.dataFormat.analyse.BDToken;
import com.xx.UI.complex.textArea.view.dataFormat.analyse.Token;
import com.xx.UI.complex.textArea.view.dataFormat.example.java.JavaImp.BlockEnumJava;
import com.xx.UI.complex.textArea.view.dataFormat.example.java.lexer.Java20Lexer;
import com.xx.UI.complex.textArea.view.dataFormat.example.java.parser.Java20ErrorListener;
import com.xx.UI.complex.textArea.view.dataFormat.example.java.parser.Java20Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BDAnalyseJava extends BDAnalyse<BlockEnumJava> {
    public BDAnalyseJava() {
        super();
    }

    @Override
    public List<BDToken<BlockEnumJava>> getBDToken(String text) {
        CharStream input = CharStreams.fromString(text);
        Java20Lexer lexer = new Java20Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();

        List<Token<BlockEnumJava>> tokenList = tokens
                .getTokens()
                .stream()
                .filter(token -> token.getType() != 123 && token.getType() != 86)
                .map(token -> Token.transform(token, BlockEnumJava.fromValue(token.getType()), token.getText()))
                .toList();
        Java20Parser parser = new Java20Parser(tokens);
        parser.removeErrorListeners();
        ArrayList<Token<BlockEnumJava>> errorTokens = new ArrayList<>();
        parser.addErrorListener(new Java20ErrorListener(errorTokens));
        ParseTreeWalker walker = new ParseTreeWalker();
        ArrayList<Token<BlockEnumJava>> rootTokens = new ArrayList<>();
        BDJavaListener listener = new BDJavaListener(rootTokens);
        walker.walk(listener, parser.start_());
        rootTokens.addAll(tokenList);
        rootTokens.addAll(errorTokens);

        Map<Integer, BDToken<BlockEnumJava>> map = new HashMap<>();
        rootTokens.forEach(token -> {
            if (token.type().getValue() == org.antlr.v4.runtime.Token.EOF) return;
            String tokenText = token.text();
            String[] split = tokenText.split("\n");
            for (int i = 0; i < split.length; i++) {
                int paraIndex = token.line() + i;
                map.computeIfAbsent(paraIndex, _ -> new BDToken<>(paraIndex)).addRange(token.type(),
                        i == 0 ? token.charPositionInLine() : 0,
                        (i == 0 ? token.charPositionInLine() : 0) + split[i].length(),
                        token.info());
            }
        });
        return map
                .values()
                .stream()
                .toList();
    }

    @Override
    public BlockEnumJava getUndefinedType() {
        return BlockEnumJava.UNDEFINED.undefinedType();
    }
}
