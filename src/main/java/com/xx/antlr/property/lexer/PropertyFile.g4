grammar PropertyFile;
file    :   prop+;
prop    :   ID '=' STRING ('\r'?'\n')?;

ID      :   [a-zA-Z_]+;
STRING  :   '"' ('\\"' | .)*?  '"';

WS      :   [ ] ->skip;

