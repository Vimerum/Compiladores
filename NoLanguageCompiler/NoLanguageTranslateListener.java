import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.v4.runtime.tree.*;

public class NoLanguageTranslateListener extends NoLanguageBaseListener {

    // Cria uma lista de Strings, que depois serao salvas no arquivo de output
    private List<String> linhas = new ArrayList<String>();

    // Mantem as variaveis criadas salvas
    // <[Nome_variavel], [Valor_variavel]>
    private Map<String, Integer> varInt = new HashMap<String, Integer>();
    private Map<String, Float> varFloat = new HashMap<String, Float>();

    @Override
    public void enterPrograma (NoLanguageParser.ProgramaContext ctx) {
        linhas.add("#include <stdio.h>");
        linhas.add("int main () {");
    }

    @Override
    public void exitPrograma (NoLanguageParser.ProgramaContext ctx) {
        linhas.add("return 0;");
        linhas.add("}");

        saveToFile();
    }

    @Override
    public void enterDeclaracoes (NoLanguageParser.DeclaracoesContext ctx) {
        int numberVars = ctx.ID().size();

        String newLine = ctx.TIPO() + " " + ctx.ID().get(0).getText() + getInitValue(ctx.TIPO().getText());
        for (int i = 1; i < numberVars; i++) {
            newLine += ", " + ctx.ID().get(i).getText() + getInitValue(ctx.TIPO().getText());
        }
        newLine += ";";

        linhas.add(newLine);
    }

    private String getInitValue (String tipo) {
        if (tipo.equals("int"))
            return (" = " + "0");
        else
            return (" = " + "0f");
    }

    private void saveToFile () {
        try {
            FileWriter file = new FileWriter("../output.c");
            BufferedWriter writer = new BufferedWriter(file);

            for (String s : linhas) {
                System.out.println(s);
                writer.write(s);
                writer.newLine();
            }

            writer.close();
            file.close();
        } catch (Exception e) {
            System.out.println("[ERRO 003]: Ocorreu um erro ao salvar o arquivo.");
            System.out.println(e.getMessage());
        }
    }
}
