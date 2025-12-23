package com.xx.antlr.Expr.ExprImp;

import com.xx.antlr.Expr.parser.ExprBaseVisitor;
import com.xx.antlr.Expr.parser.ExprParser;

import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends ExprBaseVisitor<Integer> {
    private final Map<String, Integer> memory = new HashMap<>();

    /**
     * expr op=('+'|'-') expr
     */
    @Override
    public Integer visitAddSub(ExprParser.AddSubContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if (ctx.op.getType() == ExprParser.ADD)
            return left + right;
        return left - right;
    }

    /**
     * ID '=' expr NEWLINE
     */
    @Override
    public Integer visitAssign(ExprParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        int value = visit(ctx.expr());
        memory.put(id, value);
        return value;
    }

    /**
     * NEWLINE
     */
    @Override
    public Integer visitBlank(ExprParser.BlankContext ctx) {
        return super.visitBlank(ctx);
    }

    /**
     * ID
     */
    @Override
    public Integer visitId(ExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (memory.containsKey(id)) return memory.get(id);
        return 0;
    }

    /**
     * INT
     */
    @Override
    public Integer visitInt(ExprParser.IntContext ctx) {
        return Integer.parseInt(ctx.INT().getText());
    }

    /**
     * expr op=('*'|'/') expr
     */
    @Override
    public Integer visitMulDiv(ExprParser.MulDivContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if (ctx.op.getType() == ExprParser.MUL)
            return left * right;
        return left / right;
    }

    /**
     * '(' expr ')'
     */
    @Override
    public Integer visitParens(ExprParser.ParensContext ctx) {
        return visit(ctx.expr());
    }

    /**
     * expr NEWLINE
     */
    @Override
    public Integer visitPrintExpr(ExprParser.PrintExprContext ctx) {
        Integer value = visit(ctx.expr());
        System.out.println(">> " + value); // 添加输出前缀便于识别
        return value; // 返回实际值而不是0
    }
}
