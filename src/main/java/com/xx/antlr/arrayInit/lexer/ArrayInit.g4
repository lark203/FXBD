/*
* 语法文件一般以grammar关键字开始，后面跟着文件名，然后是规则定义。
* 这是ArrayInit的语法，它必须和文件名ArrayInit.g4相匹配。
*/
grammar ArrayInit;

prog : init+;
// 规则定义
// 一条名为init的规则，它匹配一对花括号中的、逗号分隔的value。
init : '{' value (',' value)* '}';

// 一个value可以是嵌套的花括号结构，也可以是一个简单的整数，即INT词法符号
value: init
       | INT
       ;
// 语法分析器的规则必须以小写字母开头，词法分析器的规则必须以大写字母开头。
INT : [0-9]+ ; // 匹配一个或多个数字。
WS : [ \t\r\n]+ -> skip ; // 忽略空白符，包括空格、制表符、换行符，丢弃。