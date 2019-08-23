import java.io.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class NoLanguageCompiler {

    public static void main (String[] args) {
        // Confere se foi passado o numero certo de argumentos
        if (args.length == 0) {
            System.out.println("[ERRO 001]: Eh necessario um argumento para compilacao: o nome do arquivo a ser compilado.");
        } else if (args.length > 1) {
            System.out.println("[ERRO 002]: Muitos argumento foram passados, apenas um eh necessario: o nome do arquivo a ser compilado.");
        } else {
            String fileName = args[0];
            try {
                // Printa o Header
                printHeader();

                System.out.printf("Iniciando compilacao do arquivo: %s\n", fileName);
                // Cria um Stream que le o arquivo
                CharStream input = CharStreams.fromFileName(fileName);
                compile(input);

                System.out.println("Arquivo compilado com SUCESSO.\n");
                // Printa o Footer
                printFooter();
            } catch (Exception e) {
                System.out.println("[ERRO 003]: Ocorreu uma excecao:");
                System.out.println(e.getMessage());
            }
        }
    }

    /* 
     * O codigo abaixo foi retirado do livro "The Definitive ANTLR4 Reference" de Terence Parr.
     * Parr, T. (2013). The Definitive ANTLR4 Reference. 1st ed. Dallas, Texas: The Pragmatic Bookshelf, p.29.
     **/
    public static void compile (CharStream input) throws Exception {

        // Cria um lexer a partir do arquivo
        NoLanguageLexer lexer = new NoLanguageLexer(input);

        // Cria um buffer de tokens a partir do lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Cria um parser a partir dos tokens
        NoLanguageParser parser = new NoLanguageParser(tokens);
        ParseTree tree = parser.programa();

        // Cria um "andador" generico para a arvore de conversao, que pode chamar os callbacks
        ParseTreeWalker walker = new ParseTreeWalker();
        
        // Anda pela arvore criada pela traducao, chamando os callbacks
        walker.walk(new NoLanguageTranslateListener(), tree);
    }

    private static void printHeader () throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("NoLanguageHeader.txt"));

        String line = reader.readLine();

        while(line != null) {
            System.out.println(line);
            line = reader.readLine();
        }

        System.out.println();
    }

    private static void printFooter () throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("NoLanguageFooter.txt"));

        String line = reader.readLine();

        while(line != null) {
            System.out.println(line);
            line = reader.readLine();
        }
    }
}
