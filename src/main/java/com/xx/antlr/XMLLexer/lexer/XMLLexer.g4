lexer grammar XMLLexer;

/*默认的‘模式’：所有在标签之外的东西*/
OPEN        :   '<'                 ->      pushMode(INSIDE);
COMMENT     :   '</--' .*? '-->'    ->      skip;
EntityRef   :   '&' [a-z]+ ';';
TEXT        :   ~('<'|'&')+;  /*匹配任意除<与&之外的16位字符*/

/*-------------------- 所有在标签之内的东西 ------------------------*/
mode INSIDE;
CLOSE       :   '>'                 ->      popMode;/*回到默认模式*/
SLASH_CLOSE :   '/>'                ->      popMode;
EQUIALS     :   '=';
STARING     :   '"' .*? '"';
SlashName   :   '/' Name;
Name        :   ALPHA   (ALPHA|DIGIT)*;
S           :   [ \t\r\n]           ->      skip;

fragment
ALPHA       :   [a-zA-Z];

fragment
DIGIT       :   [0-9];