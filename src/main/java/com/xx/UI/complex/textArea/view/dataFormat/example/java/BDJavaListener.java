package com.xx.UI.complex.textArea.view.dataFormat.example.java;

import com.xx.UI.complex.textArea.view.dataFormat.analyse.Token;
import com.xx.UI.complex.textArea.view.dataFormat.example.java.JavaImp.BlockEnumJava;
import com.xx.UI.complex.textArea.view.dataFormat.example.java.parser.Java20Parser;
import com.xx.UI.complex.textArea.view.dataFormat.example.java.parser.Java20ParserBaseListener;

import java.util.List;

public class BDJavaListener extends Java20ParserBaseListener {
    private final List<Token<BlockEnumJava>> tokens;

    public BDJavaListener(List<Token<BlockEnumJava>> tokens) {
        this.tokens = tokens;
    }

    // 2. 处理类字段声明
    @Override
    public void enterFieldDeclaration(Java20Parser.FieldDeclarationContext ctx) {
        super.enterFieldDeclaration(ctx);
        tokens.add(Token.transform(ctx.variableDeclaratorList().start, BlockEnumJava.FIELD_NAME, null));
    }

    @Override
    public void enterMethodDeclaration(Java20Parser.MethodDeclarationContext ctx1) {
        super.enterMethodDeclaration(ctx1);

        // 提取方法名
        Java20Parser.MethodDeclaratorContext methodDeclarator = ctx1.methodHeader().methodDeclarator();
        if (methodDeclarator != null) {
            Java20Parser.IdentifierContext ctx = methodDeclarator.identifier();
            if (ctx != null) {
                tokens.add(Token.transform(ctx.start, BlockEnumJava.METHOD_NAME, null));
            }
        }
    }

    @Override
    public void enterAnnotation(Java20Parser.AnnotationContext ctx) {
        super.enterAnnotation(ctx);
        tokens.add(new Token<>(ctx.getText()
                , BlockEnumJava.fromValue(ctx.getStart().getType())
                , ctx.getStart().getLine() - 1
                , ctx.getStart().getCharPositionInLine()
                , null));
    }
}
