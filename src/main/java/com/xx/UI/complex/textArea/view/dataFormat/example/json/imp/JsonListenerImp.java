package com.xx.UI.complex.textArea.view.dataFormat.example.json.imp;

import com.xx.UI.complex.textArea.view.dataFormat.example.json.parser.JSONBaseListener;
import com.xx.UI.complex.textArea.view.dataFormat.example.json.parser.JSONParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class JsonListenerImp extends JSONBaseListener {
    private final String EMPTY = "";
    private final ParseTreeProperty<String> xml = new ParseTreeProperty<>();

    public String getXML(ParseTree ctx) {
        return xml.get(ctx);
    }

    private void putXML(ParseTree ctx, String value) {
        xml.put(ctx, value);
    }

    private String stripQuotes(String s) {
        if (s.startsWith("\"")) s = s.substring(1);
        if (s.endsWith("\"")) s = s.substring(0, s.length() - 1);
        return s;
    }

    @Override
    public void exitAtom(JSONParser.AtomContext ctx) {
        putXML(ctx, ctx.getText());
        super.exitAtom(ctx);
    }

    @Override
    public void exitString(JSONParser.StringContext ctx) {
        putXML(ctx, stripQuotes(ctx.STRING().getText()));
        super.exitString(ctx);
    }


    @Override
    public void exitPair(JSONParser.PairContext ctx) {
        String tag = stripQuotes(ctx.STRING().getText());
        JSONParser.ValueContext value = ctx.value();
        String x = String.format("<%s>%s</%s>\n", tag, getXML(value), tag);
        putXML(ctx, x);
        super.exitPair(ctx);
    }

    @Override
    public void exitAnObject(JSONParser.AnObjectContext ctx) {
        StringBuilder buf = new StringBuilder();
        buf.append("\n");
        ctx.pair().forEach(pair -> buf.append(getXML(pair)));
        putXML(ctx, buf.toString());
        super.exitAnObject(ctx);
    }

    @Override
    public void exitEmptyObject(JSONParser.EmptyObjectContext ctx) {
        putXML(ctx, EMPTY);
        super.exitEmptyObject(ctx);
    }

    @Override
    public void exitObjectValue(JSONParser.ObjectValueContext ctx) {
        putXML(ctx, getXML(ctx.object()));
        super.exitObjectValue(ctx);
    }

    @Override
    public void exitArrayOfValues(JSONParser.ArrayOfValuesContext ctx) {
        StringBuilder buf = new StringBuilder();
        ctx.value().forEach(value -> {
            buf.append("\n");
            buf.append("<element>");
            buf.append(getXML(value));
            buf.append("</element>");
        });
        putXML(ctx, buf.toString());
        super.exitArrayOfValues(ctx);
    }

    @Override
    public void exitEmptyArray(JSONParser.EmptyArrayContext ctx) {
        putXML(ctx, EMPTY);
        super.exitEmptyArray(ctx);
    }

    @Override
    public void exitArrayValue(JSONParser.ArrayValueContext ctx) {
        putXML(ctx, getXML(ctx.array()));
        super.exitArrayValue(ctx);
    }

    @Override
    public void exitJson(JSONParser.JsonContext ctx) {
        putXML(ctx, getXML(ctx.getChild(0)));
        super.exitJson(ctx);
    }
}
