grammar NoLanguage;

programa: 'inicio-programa' (declaracoes)* bloco 'fim-programa';

declaracoes: TIPO ID (',' ID)* ';';

bloco: (comando)+;

comando: ( comandoLer
         | comandoMostrar
         | comandoDefinir
         | comandoCondicional
         | comandoEnquanto
         | comandoFacaEnquanto );

// Comandos

comandoLer: 'ler' '(' ID ');';

comandoMostrar: 'mostrar' '(' (ID | STRING | expressao) ');';

comandoDefinir: ID '=' expressao ';';

comandoCondicional: comandoSe (comandoSenao)?;
comandoSe: 'se' '(' condicao ')' '{' (bloco)+ '}';
comandoSenao: 'senao' '{' (bloco)+ '}';

comandoEnquanto: 'enquanto' '(' condicao ')' '{' (bloco)+ '}';

comandoFacaEnquanto: 'faca' '{' (bloco)+ '}' 'enquanto' '(' condicao ');';

// Expressoes

condicao: expressao OPERADOR expressao;

expressao: operadorNivel1;

operadorNivel1: operadorNivel2 ( NIVEL1 operadorNivel2)*;

operadorNivel2: termo ( NIVEL2 termo)*;

termo: NUMERO | ID | ('(' expressao ')');

WS: [ \t\r\n]+ -> skip;

// Constantes

TIPO: ('int' | 'float');

NIVEL1: ('+' | '-');

NIVEL2: ('*' | '/');

OPERADOR: ('<' | '<=' | '==' | '!=' | '>=' | '>' | '&&' | '||');

ID: [a-z][a-zA-Z0-9]*;

NUMERO: ('-')? [0-9]+ ('.' [0-9]*)?;

STRING: '"' (~[\\"\r\n])* '"';
