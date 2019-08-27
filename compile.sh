#! /bin/bash

## Variavel para controle se existe o ANTLR
antlr_exists=false

for f in /usr/local/lib/antlr*; do
    
    ## Confere se existe alguma biblioteca do ANTRL instalada
    if [ -e "$f" ]; then
        antlr_exists=true
    else
        antlr_exists=false
    fi

    break
done

if [ "$antlr_exists" = false ]; then
    echo "ATENÇÃO! Nenhuma biblioteca do ANTLR foi encontrada na pasta /usr/local/lib/"
    echo "Consulte o README.md para mais instruções"
else
    ## Roda o ANTLR
    java -Xmx500M -cp "/usr/local/lib/antlr-4.7.1-complete.jar:$CLASSPATH" org.antlr.v4.Tool ./NoLanguageCompiler/NoLanguage.g4

    ## Compila os arquivos java
    javac ./NoLanguageCompiler/NoLanguage*.java

    ## Salva o caminho do arquivo a ser compilado
    file_path="../$1"

    ## Entra na pasta do compilador
    cd NoLanguageCompiler

    ## Roda o compilador
    java NoLanguageCompiler $file_path

    ## Volta para a pasta raiz do projeto
    cd ..
fi
