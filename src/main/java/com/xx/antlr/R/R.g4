grammar R;

prog    :   (   expr_or_assign (';' | NL)
            |   NL
            )*
            EOF;

expr_or_assign
        :   expr ('<-' | '=' | '<<-') expr_or_assign
        |   expr
        ;

expr    :   expr '[[' sublist ']'']'        # SubsetDouble
        |   '[' sublist ']'                 # SubsetSingle
        |   expr ('::' | ':::') expr        # NamespaceOp
        |   expr ('$' | '@') expr           # SlotOp
        |   expr '^'<assoc=right> expr      # PowerOp
        |   ('-' | '+') expr                # UnaryOp
        |   expr ':' expr                   # SequenceOp
        |   expr USER_OP expr               # UserOp
        |   expr ('*' | '/') expr           # MulDivOp
        |   expr ('+' | '-') expr           # AddSubOp
        |   expr ('>' | '>=' | '<' | '<=' | '==' | '!=') expr # ComparisonOp
        |   '!' expr                        # NotOp
        |   expr ('&' | '&&') expr          # AndOp
        |   expr ('|' | '||') expr          # OrOp
        |   '~' expr                        # TildeOp
        |   expr '~' expr                   # FormulaOp
        |   expr ('->' | '->>' | ':=') expr # AssignmentOp
        |   '{' exprlist '}'                # Block
        |   'if' '(' expr ')' expr          # If
        |   'if' '(' expr ')' expr 'else' expr # IfElse
        |   'for' '(' ID 'in' expr ')' expr # ForLoop
        |   'while' '(' expr ')' expr       # WhileLoop
        |   'repeat' expr                   # RepeatLoop
        |   '?' expr                        # Help
        |   'next'                          # Next
        |   'break'                         # Break
        |   'function' '(' formlist? ')' expr # FunctionDef
        |   expr '(' sublist ')'            # FunctionCall
        |   ID                              # Identifier
        |   NUM                             # Number
        |   STRING                          # String
        |   NULL_CONST                      # Null
        |   BOOL                            # Boolean
        |   INF                             # Infinity
        |   NAN                             # NaN
        |   '(' expr ')'                    # ParenExpr
        ;

exprlist:   expr_or_assign ((';' | NL) expr_or_assign?)* ;

formlist:   form (',' form)* ;

form    :   ID
        |   ID '=' expr
        |   '...'
        ;

sublist :   sub (',' sub)* ;

sub     :   expr
        |   ID
        |   ID '=' expr
        |   STRING '=' expr?
        |   NULL_CONST '=' expr?
        |   '...'
        ;

// 词法规则
NULL_CONST: 'NULL' | 'NA' | 'Inf' | 'NaN' ;
BOOL    : 'TRUE' | 'FALSE' ;
INF     : 'Inf' ;
NAN     : 'NaN' ;
USER_OP : '%' [a-zA-Z_]+ '%' ;  // 用户定义运算符如 %*%

ID      : '.' (LETTER | '_' | '.') (LETTER | DIGIT | '_' | '.')*
        | LETTER (LETTER | DIGIT | '_' | '.')*
        ;

NUM     : DIGIT+ ('.' DIGIT*)? ([eE] [-+]? DIGIT+)?  // 整数/浮点数
        | DIGIT* '.' DIGIT+ ([eE] [-+]? DIGIT+)?
        | DIGIT+ [i]              // 复数 (e.g., 3i)
        ;

STRING  : '"' ( ESC | . )*? '"'   // 双引号字符串
        | '\'' ( ESC | . )*? '\''  // 单引号字符串
        ;

fragment ESC : '\\' [btnfr"'\\] ;  // 转义字符

NL      : '\r'? '\n' ;
COMMENT : '#' .*? NL -> skip ;    // 行注释
WS      : [ \t]+ -> skip ;        // 空白字符

fragment LETTER : [a-zA-Z] ;
fragment DIGIT  : [0-9] ;