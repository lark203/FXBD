package com.xx.antlr.arrayInit.astImp;

import com.xx.antlr.arrayInit.parser.ArrayInitBaseListener;
import com.xx.antlr.arrayInit.parser.ArrayInitParser;

public class ArrayInitBaseListenerImp extends ArrayInitBaseListener {
    @Override
    public void enterInit(ArrayInitParser.InitContext ctx) {
        super.enterInit(ctx);
        System.out.print("\"");
    }

    @Override
    public void exitInit(ArrayInitParser.InitContext ctx) {
        super.exitInit(ctx);
        System.out.print("\"");
    }

    @Override
    public void enterValue(ArrayInitParser.ValueContext ctx) {
        super.enterValue(ctx);
        System.out.printf("\\u%04x", Integer.parseInt(ctx.INT().getText()));
    }
}
