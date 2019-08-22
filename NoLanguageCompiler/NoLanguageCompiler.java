import java.io.FileReader;

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
                FileReader file = new FileReader(fileName);
                compile(file);
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
    public static void compile (FileReader file) throws Exception {
        // Cria um Stream que le o arquivo
        ANTLRInputStream input = new ANTLRInputStream(file);

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
        System.out.println("Acabou");
    }
}
