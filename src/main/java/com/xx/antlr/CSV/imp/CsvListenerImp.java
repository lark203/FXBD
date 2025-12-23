package com.xx.antlr.CSV.imp;

import com.xx.antlr.CSV.parser.CSVBaseListener;
import com.xx.antlr.CSV.parser.CSVParser;
import org.antlr.v4.misc.OrderedHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CsvListenerImp extends CSVBaseListener {
    private final static String EMPTY = "";
    private final ArrayList<Map<String, String>> rows = new ArrayList<>();
    private List<String> header;
    private List<String> currentRow;


    @Override
    public void enterRow(CSVParser.RowContext ctx) {
        currentRow = new ArrayList<>();
        super.enterRow(ctx);
    }

    @Override
    public void enterString(CSVParser.StringContext ctx) {
        currentRow.add(ctx.STRING().getText());
        super.enterString(ctx);
    }

    @Override
    public void enterText(CSVParser.TextContext ctx) {
        currentRow.add(ctx.TEXT().getText());
        super.enterText(ctx);
    }

    @Override
    public void enterEmpty(CSVParser.EmptyContext ctx) {
        currentRow.add(EMPTY);
        super.enterEmpty(ctx);
    }

    @Override
    public void enterHdr(CSVParser.HdrContext ctx) {
        header = new ArrayList<>();
        super.enterHdr(ctx);
    }

    @Override
    public void exitHdr(CSVParser.HdrContext ctx) {
        header.addAll(currentRow);
        super.exitHdr(ctx);
    }

    @Override
    public void exitRow(CSVParser.RowContext ctx) {
//        判断当前的行是否属于标题行。两个判断方法均可。
        if (ctx.parent instanceof CSVParser.HdrContext || ctx.getParent().getRuleIndex() == CSVParser.RULE_hdr) return;
        AtomicInteger i = new AtomicInteger(0);
        Map<String,String> row = new OrderedHashMap<>();
        header.forEach(e-> row.put(e,currentRow.get(i.getAndIncrement())));
        rows.add(row);
        super.exitRow(ctx);
    }

    public ArrayList<Map<String, String>> getRows() {
        return rows;
    }
}
