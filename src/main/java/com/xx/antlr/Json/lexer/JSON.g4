grammar JSON;

props   :   json+;

//      语法
json
        :   object
        |   array
        ;
object
        :   '{' pair (',' pair)* '}'     # AnObject
        |   '{' '}'                     # EmptyObject
        ;

pair    :   STRING ':' value;

array
        :   '[' value (',' value)* ']'  # ArrayOfValues
        |   '[' ']'                     # EmptyArray
        ;

value
        :   STRING                      # String
        |   NUMBER                      # Atom
        |   object                      # ObjectValue
        |   array                       # ArrayValue
        |   'true'                      # Atom
        |   'false'                     # Atom
        |   'null'                      # Atom
        ;

//      词法
STRING  :   '"' (ESC | ~["\\])* '"'; //" 转义字符 | 不是双引号或反斜杠的普通字符 "
fragment    ESC :   '\\' (["\\/bfnrt] | UNICODE); // 所有转义字符
fragment    UNICODE :   'u' HEX HEX HEX HEX; // UNICODE码
fragment    HEX :   [0-9a-fA-F];

NUMBER
        :   '-'? INT '.' INT EXP? // 1.35 1.35E-9 0.3 -4.5
        |   '-'? INT EXP // 1e10 -3e4
        |   '-'? INT // -3 45
        ;
fragment    INT :   '0' | [1-9] [0-9]*; // 非零整数不以0开头。
fragment    EXP :   [Ee] [+\-]? INT; //[Ee]表示E或e开头   \- 是 -的转义，-用于表达范围。

WS  :   [ \t\n\r]+  -> skip;
