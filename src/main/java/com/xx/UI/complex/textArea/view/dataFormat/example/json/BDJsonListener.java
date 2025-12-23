package com.xx.UI.complex.textArea.view.dataFormat.example.json;

import com.xx.UI.complex.textArea.view.dataFormat.analyse.Token;
import com.xx.UI.complex.textArea.view.dataFormat.example.json.imp.BlockEnumJson;
import com.xx.UI.complex.textArea.view.dataFormat.example.json.lexer.JSONLexer;
import com.xx.antlr.Json.parser.JSONBaseListener;
import com.xx.antlr.Json.parser.JSONParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BDJsonListener extends JSONBaseListener {
    private final ArrayList<Token<BlockEnumJson>> rootTokens;
    private final Set<TerminalNode> keyNodes = new HashSet<>();

    public BDJsonListener(ArrayList<Token<BlockEnumJson>> rootTokens) {
        this.rootTokens = rootTokens;
    }

    @Override
    public void enterPair(JSONParser.PairContext ctx) {
        // 标记这个键值对中的 STRING 作为键
        if (ctx.STRING() != null) {
            keyNodes.add(ctx.STRING());
        }
        super.enterPair(ctx);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        // 处理所有终结符节点
        if (node.getSymbol().getType() == JSONLexer.STRING) {
            // 检查这个字符串是否是键
            if (keyNodes.contains(node)) {
                // 这是键
                Token<BlockEnumJson> token = Token.transform(
                    node.getSymbol(),
                    BlockEnumJson.KEY,
                    node.getText()
                );
                rootTokens.add(token);
            } else {
                // 这是字符串值
                Token<BlockEnumJson> token = Token.transform(
                    node.getSymbol(),
                    BlockEnumJson.STRING,
                    node.getText()
                );
                rootTokens.add(token);
            }
        } else {
            // 处理其他类型的token
            BlockEnumJson type = BlockEnumJson.fromValue(node.getSymbol().getType());
            if (type != BlockEnumJson.UNDEFINED) {
                Token<BlockEnumJson> token = Token.transform(
                    node.getSymbol(),
                    type,
                    node.getText()
                );
                rootTokens.add(token);
            }
        }
    }

    @Override
    public void exitPair(JSONParser.PairContext ctx) {
        // 处理完键值对后，移除已处理的键
        if (ctx.STRING() != null) {
            keyNodes.remove(ctx.STRING());
        }
        super.exitPair(ctx);
    }
}