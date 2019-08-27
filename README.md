# Compilador da linguagem NoLanguage

## Integrantes

* André Aranovich Florentino - RA: 11011716
* Lucas Eduardo Gonçalves da Rosa - RA: 11076916
* Vinícius Veronese Gonçalves - RA: 11040816

## Sumário

1. [Introdução](#introducao)
2. [Gramática](#gramatica)

    2.1 [Estrutura geral](#estrutura-geral)

    2.2 [Variaveis](#variaveis)

    2.3 [Bloco de Código](#bloco-de-codigo)

3. [Instruções](#instrucoes)

    3.1 [Pré-requisitos](#pre-requisitos)

    3.2 [Compilar um programa](#compilar-programa)

    3.3 [Exemplos](#exemplos)

4. [Resolução de problemas](#resolucao-de-problemas)

## Introdução <a name="introducao"></a>

Esse compilador foi feito para a disciplina de Compiladores 2019.2 (MCTA007-17) ministrada pelo Prof. Dr. Valério Ramos Batista
na Universidade Federal do ABC.

O grupo criou uma linguagem com as caracteristicas necessárias e a chamou de NoLanguage. Este compilador traduz o código da 
linguagem NoLanguage para a linguagem C.

## Gramática <a name="gramatica"></a>

O arquivo deve ter a extensao *.nl* e deve estar na raiz do projeto.

### Estrutura geral <a name="estrutura-geral"></a>

Para criar um programa em NoLanguage deve-se seguir a seguinte estrutura:

```NoLanguage
inicio-programa
    [declarações de variaveis]

    [bloco de código]
fim-programa
```

### Variaveis <a name="variaveis"></a>

A declaração de variaveis deve ser da seguinte forma:

```NoLanguage
[tipo-da-variavel] [ID];
```

Onde:

* **tipo-da-variavel:** Pode ser int ou float;

* **ID:** O nome da variavel declarada, que deve ser iniciado com uma letra minuscula.

### Bloco de Código <a name="bloco-de-codigo"></a>

O bloco de codigo é constituido por uma sequencia de comandos. Os comando disponiveis são:

* **[ID] = [Expressao];** : Atribui o valor de uma expressao *Expressao* a uma variavel *ID*;

* **ler ([ID]);** : Le um input do usuario e armazena na variavel *ID*;

* **mostrar ([ID | STRING | Expressao]);** : Imprime uma variavel *ID*, uma sequencia de chars *STRING* ou uma expressao *Expressao*;

* **se ([Condicao]) { [Bloco de Codigo] }** : Executa o *Bloco de Codigo* se o resultado da *Condicao* for verdadeiro;

* **senao { [Bloco de Codigo] }** : Caso a *Condicao* do comando *se* anterior não for verdadeira, executa o *Bloco de Codigo*;

* **enquanto ([Condicao]) { [Bloco de Codigo] }** : Executa o *Bloco de Codigo* enquanto a *Condicao* for verdadeira;

* **faca { [Bloco de Codigo] } enquanto ([Condicao]);** : Executa o *Bloco de Codigo* enquanto a *Condicao* for verdadeira.

## Instruções <a name="instrucoes"></a>

### Pré-requisitos <a name="pre-requisitos"></a>

**ATENÇÃO!** É necessário que o ANTLR4 esteja instalado na pasta */usr/local/lib/*. Para maiores informações de como instala-lo, siga as instruções
disponiveis no [repositório oficial](https://github.com/antlr/antlr4/blob/master/doc/getting-started.md) do ANTLR.

### Compilar um programa <a name="compilar-programa"></a>

Para compilar o código em NoLanguage e converte-lo para C siga os seguintes passos:

1. Autorize o script *compile.sh* a executar, esse passo precisa ser executado apenas uma vez. Para isso rode o seguinte comando:

```
chmod +x compile.sh
```

2. Execute o script *compile.sh*, passando como argumento o nome do arquivo a ser compilado. Para isso rode o seguinte comando:

```
./compile.sh [nome-do-arquivo]
```

Onde *[nome-do-arquivo]* é o nome do arquivo a ser compilado (sem os colchetes).

3. Após o termino da compilação, se não ouver nenhum erro, o código em c estará salvo em um arquivo chamado *output.c* na pasta
raiz do projeto.

### Exemplos <a name="exemplos"></a>

Estão sendo disponibilizados quatro exemplos:

* **01.nl :** Le dois números do usuario, imprime o maior deles;

* **02.nl :** Imprime "Olá mundo";

* **03.nl :** Le um número *x* do usuário e imprime seus primeiros dez multiplos de *x*;

* **04.nl :** Le um número *x* do usuário e imprime todos os números de 0 até *x*.

## Resolução de problemas <a name="resolucao-de-problemas"></a>

Existem alguns códigos de erros que podem aparecer, sendo eles:

* **[ERRO 001]:** Foi feita uma tentativa de compilação sem passar o argumento necessário, o nome do arquivo;

* **[ERRO 002]:** Foram passados mais de um argumento para compilar. **ATENÇÃO:** Caso use o script *compile.sh* para compilar, os 
outros argumento serão ignorados, sendo assim, esse erro não aparecerá. Esse erro apenas aparece caso seja passado mais de um 
argumento enquanto tenta compilar usando direto o arquivo java *NoLanguageCompiler.java*;

* **[ERRO 003]:** Ocorreu um erro ao abrir o arquivo informado como parametro da compilação. Talvez o nome do arquivo esteja errado 
ou ele não esta na pasta raiz do projeto;

* **[ERRO 004]:** O argumento passado como parametro da compilação não é um arquivo NoLanguage. Os programas NoLanguage devem terminar em *.nl*.

* **[ERRO 005]:** Ocorreu um erro ao tentar salvar o arquivo *output.c*.
