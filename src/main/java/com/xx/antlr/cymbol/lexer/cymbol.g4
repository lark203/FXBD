grammar cymbol;
props   : file+;

file    :   (varDecl | functionDecl)+;

varDecl :   type ID ( '=' expr)? ';';
type    :   'int' | 'char' | 'void' | 'float';

functionDecl
        :   type ID '(' formalParameters? ')' block
        ;
formalParameters
        :   formalParameter (',' formalParameter)*
        ;
formalParameter
        :   type ID
        ;

block   :   '{' stat* '}';
stat    :   block
        |   varDecl
        |   'if' expr 'then' stat ('else' stat)?
        |   'return' expr? ';'
        |   expr '=' expr? ';'
        |   expr ';';

expr    :   ID '(' exprList? ')'        # Call    //  参数调用，f()...
        |   expr '[' expr ']'           # Index   //  数组，a[1][x-4]...
        |   '-' expr                    # Negate
        |   '!' expr                    # Not
        |   expr '*' expr               # Mult
        |   expr ('+' | '-') expr       # AddSub
        |   expr '==' expr              # Equal
        |   ID                          # Var
        |   INT                         # Int
        |   '(' expr ')'                # Parens
        ;
exprList    :   expr (',' expr)*;

ID          :   LETTER (LETTER|DIGIT)*;
INT         :   ([1-9] DIGIT* ) | [0];
fragment
LETTER      :   [a-zA-Z\u0080-\u00FF_];
fragment
DIGIT       :   [0-9];

WS          :   [ \t\n\r] -> skip;