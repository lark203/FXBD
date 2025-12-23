package com.xx.antlr.cymbol.imp;

import com.xx.antlr.cymbol.parser.cymbolBaseListener;
import com.xx.antlr.cymbol.parser.cymbolParser;
import org.antlr.v4.runtime.misc.MultiMap;
import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.util.Set;

public class CymbolImp extends cymbolBaseListener {
    Set<String> nodes = new OrderedHashSet<>();
    MultiMap<String,String> edges = new MultiMap<>();
    private String currentFunction = null;
    public void edge(String source,String target){
        edges.map(source,target);
    }

    public Set<String> getNodes() {
        return nodes;
    }

    public MultiMap<String, String> getEdges() {
        return edges;
    }

    @Override
    public void enterFunctionDecl(cymbolParser.FunctionDeclContext ctx) {
        currentFunction = ctx.ID().getText();
        nodes.add(currentFunction);
        super.enterFunctionDecl(ctx);
    }

    @Override
    public void exitCall(cymbolParser.CallContext ctx) {
        edge(currentFunction,ctx.ID().getText());
        super.exitCall(ctx);
    }
}
