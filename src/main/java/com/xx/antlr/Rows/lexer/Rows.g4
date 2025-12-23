grammar Rows;

@parser::members{ // 在生成的RowsParser中添加一些成员
    int col;
    public RowsParser(TokenStream input,int col){
//    自定义构造器
        this(input);
        this.col = col;
    }
}

props: file+;
file: (row NL)+;

row
locals [int i=0]
    :
    (STUFF{
    $i++;
    if($i == col) System.out.println($STUFF.text);
    })+
    ;

TAB  :   '\t' -> skip;// 匹配但是不将其传递给语法分析器。
NL   :   '\r' ? '\n'; // 匹配并将其传递给语法分析器。
STUFF:   ~[\t\r\n]+;  // 匹配出了tab与换行符之外的字符。