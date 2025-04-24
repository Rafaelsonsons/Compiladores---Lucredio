lexer grammar T1Lexer;

PALAVRA_CHAVE
    :    'declare' | 'algoritmo' | 'inteiro' | 'literal'  | 'leia' | 'se' | 'entao' | 'senao' | 'fim_se'
    | 'escreva' | 'fim_algoritmo' | 'e' | 'ou' | 'nao' | 'real' | 'logico' | 'caso' | 'seja' | 'fim_caso' | 'para' | 'fim_para'
    | 'ate' | 'faca' | 'enquanto' | 'fim_enquanto' | 'registro' | 'fim_registro' | 'tipo' | 'procedimento' | 'fim_procedimento'
    | 'var' | 'funcao' | 'fim_funcao' | 'retorne' | 'constante' | 'falso' | 'verdadeiro';

NUM_INT	: ('0'..'9')+;

NUM_REAL	: ('0'..'9')+ ('.' ('0'..'9')+)?;

IDENT : ('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*;

ABRE_COL: '[';
FECHA_COL: ']';
DOIS_PONTOS : '..';
ABRE_PAR       : '(';
FECHA_PAR      : ')';
VIRG          : ',';
DELIM   : ':';

OP_ARIT    :    '+' | '-' | '*' | '/' | '&' | '%' | '^' ;
OP_REL    :    '>' | '>=' | '<' | '<=' | '<>' | '='    ;
OP_ATR  :   '<-' ;
OP_ACESSO : '.' ;

COMENTARIO
    : '{' ~('\n'|'\r'|'{'|'}' )* '}' '\r'? '\n'? {skip();};

CADEIA 	: '"' (~( '"'|'\\' |'\n'|'\r')| ESC_SEQ)* '"';

// Espaços e quebras de linha (ignorados)
WS : ( ' ' | '\t' | '\r' | '\n' ) {skip();};
fragment
ESC_SEQ	: '\\\'';
ERRO                :   .;

CADEIA_ABERTA       : '"' (~( '"'|'\\' |'\n'|'\r')| ESC_SEQ)* '\r'? '\n'?;

COMENTARIO_ABERTO   : '{' ~('\n'|'\r'|'{'|'}' )* '\r'? '\n'?;
