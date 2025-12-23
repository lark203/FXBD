package com.xx.UI.complex.textArea.view.dataFormat.example.json;

import com.xx.UI.complex.textArea.view.dataFormat.analyse.BDAnalyse;
import com.xx.UI.complex.textArea.view.dataFormat.analyse.BDToken;
import com.xx.UI.complex.textArea.view.dataFormat.analyse.Token;
import com.xx.UI.complex.textArea.view.dataFormat.example.java.BDJavaListener;
import com.xx.UI.complex.textArea.view.dataFormat.example.java.parser.Java20ErrorListener;
import com.xx.UI.complex.textArea.view.dataFormat.example.json.imp.BlockEnumJson;
import com.xx.antlr.Json.lexer.JSONLexer;
import com.xx.antlr.Json.parser.JSONParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BDAnalyseJson extends BDAnalyse<BlockEnumJson> {

    @Override
    public List<BDToken<BlockEnumJson>> getBDToken(String text) {
        CharStream input = CharStreams.fromString(text);
        JSONLexer lexer = new JSONLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();

        List<Token<BlockEnumJson>> list = tokens
                .getTokens()
                .stream()
                .map(token -> Token.transform(token, BlockEnumJson.fromValue(token.getType()), token.getText()))
                .filter(token -> token.type() != null)
                .toList();

        JSONParser parser = new JSONParser(tokens);
        parser.removeErrorListeners();
        ParseTreeWalker walker = new ParseTreeWalker();
        ArrayList<Token<BlockEnumJson>> rootTokens = new ArrayList<>();
        BDJsonListener listener = new BDJsonListener(rootTokens);
        walker.walk(listener, parser.json());
        rootTokens.addAll(0,list);

        Map<Integer, BDToken<BlockEnumJson>> map = new HashMap<>();
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
    public BlockEnumJson getUndefinedType() {
        return BlockEnumJson.UNDEFINED;
    }
}
