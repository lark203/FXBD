grammar CSV;
props:file+;

file : hdr row+;
hdr : row;
row : field (',' field)* '\r'? '\n'; /*\r\n是widows的换行符*/
field
    :   TEXT        #text
    |   STRING      #string
    |               #empty
    ;

TEXT : ~[,\n\r"]+;// 匹配除了逗号换行双引号以外的内容。
STRING : '"' ('""'|~'"')* '"';// 匹配字符串，且允许双引号之间存在双引号。