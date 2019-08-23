import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.*;

import org.antlr.v4.runtime.tree.*;

public class NoLanguageTranslateListener extends NoLanguageBaseListener {

    // Cria uma lista de Strings, que depois serao salvas no arquivo de output
    private List<String> linhas = new ArrayList<String>();

    // Mantem as variaveis criadas salvas
    // <[Nome_variavel], [Valor_variavel]>
    private Set<String> varInt   = new HashSet<String>();
    private Set<String> varFloat = new HashSet<String>();

    // O nivel de identacao
    private int identationLevel = 0;

    private void addLinha (String newLine) {
        String ident = "";
        for (int i = 0; i < identationLevel; i++)
            ident += "\t";
        
        newLine = ident + newLine;
        linhas.add(newLine);
    }

    @Override
    public void enterPrograma (NoLanguageParser.ProgramaContext ctx) {
        addLinha("#include <stdio.h>");
        addLinha("int main () {");
        identationLevel++;
    }

    @Override
    public void exitPrograma (NoLanguageParser.ProgramaContext ctx) {
        addLinha("return 0;");
        identationLevel--;
        addLinha("}");

        saveToFile();
    }

    @Override
    public void enterDeclaracoes (NoLanguageParser.DeclaracoesContext ctx) {
        int numberVars = ctx.ID().size();
        String type = ctx.TIPO().getText();
        String newLine = "";

        for (int i = 0; i < numberVars; i++) {
            // Pega o id a ser adicionado
            String id = ctx.ID().get(i).getText();

            // Verifica se a variavel ja foi declarada
            if (varInt.contains(id) || varFloat.contains(id))
                throw new RuntimeException("Variavel duplicada: " + id + " ja foi declarada.");

            // Adiciona inicio da linha ou virgula
            if (newLine.isEmpty())
                newLine = type + " ";
            else
                newLine += ", ";

            // Adiciona a variavel para ser salva no arquivo depois
            newLine += id + getInitValue(type);

            // Adiciona a primeira variavel na lista de variaveis
            addVar(type, id);
        }
        newLine += ";";

        addLinha(newLine);
    }

    @Override
    public void enterComandoLer (NoLanguageParser.ComandoLerContext ctx) {

        String varName = ctx.ID().getText();
        String newLine = "scanf(\"%";

        if (varInt.contains(varName)) {
            newLine += "d\", &" + varName + ");";
        } else if (varFloat.contains(varName)) {
            newLine += "f\", &" + varName + ");";
        } else {
            throw new RuntimeException("Variavel nao declarada: " + varName + " nao foi declarada.");
        }
    }

    @Override
    public void enterComandoMostrar (NoLanguageParser.ComandoMostrarContext ctx) {
        String newLine = "printf(";

        if (ctx.ID() != null) {
            if (varInt.contains(ctx.ID().getText()))
                newLine += "\"%d\", " + ctx.ID().getText() + ");";
            else if (varFloat.contains(ctx.ID().getText()))
                newLine += "\"%f\", " + ctx.ID().getText() + ");";
            else
                throw new RuntimeException("Variavel nao declarada: " + ctx.ID().getText() + " nao foi declarada.");
        } else if (ctx.STRING() != null) {
            newLine += ctx.STRING().getText() + ");";
        } else {
            newLine += getExpressao(ctx.expressao()) + ");";
        }

        addLinha(newLine);
    }

    @Override
    public void enterComandoDefinir (NoLanguageParser.ComandoDefinirContext ctx) {

        String id = ctx.ID().getText();

        if (!varInt.contains(id) && !varFloat.contains(id))
            throw new RuntimeException("Variavel nao declarada: " + ctx.ID().getText() + " nao foi declarada.");

        String newLine = id + " = " + getExpressao(ctx.expressao()) + ";";

        addLinha(newLine);
    }

    @Override
    public void enterComandoSe (NoLanguageParser.ComandoSeContext ctx) {
        String newLine = "if (" + getCondicao(ctx.condicao()) + ") {";

        addLinha(newLine);
        identationLevel++;
    }

    @Override
    public void exitComandoSe (NoLanguageParser.ComandoSeContext ctx) {
        identationLevel--;
        addLinha("}");
    }

    @Override
    public void enterComandoSenao (NoLanguageParser.ComandoSenaoContext ctx) {
        addLinha("else {");
        identationLevel++;
    }

    @Override
    public void exitComandoSenao (NoLanguageParser.ComandoSenaoContext ctx) {
        identationLevel--;
        addLinha("}");
    }

    @Override
    public void enterComandoEnquanto (NoLanguageParser.ComandoEnquantoContext ctx) {
        String newLine = "while (" + getCondicao(ctx.condicao()) + ") {";

        addLinha(newLine);
        identationLevel++;
    }

    @Override
    public void exitComandoEnquanto (NoLanguageParser.ComandoEnquantoContext ctx) {
        identationLevel--;
        addLinha("}");
    }

    @Override
    public void enterComandoFacaEnquanto (NoLanguageParser.ComandoFacaEnquantoContext ctx) {
        addLinha("do {");
        identationLevel++;
    }

    @Override
    public void exitComandoFacaEnquanto (NoLanguageParser.ComandoFacaEnquantoContext ctx) {
        identationLevel--;

        String newLine = "} while (" + getCondicao(ctx.condicao()) + ");";
        addLinha(newLine);
    }

    private String getCondicao (NoLanguageParser.CondicaoContext ctx) {

        String newLine = getExpressao(ctx.expressao(0));
        newLine += " " + ctx.OPERADOR().getText() + " ";
        newLine += getExpressao(ctx.expressao(1));

        return newLine;
    }

    private String getExpressao (NoLanguageParser.ExpressaoContext ctx) {
        NoLanguageParser.OperadorNivel1Context nivel1ctx = ctx.operadorNivel1();

        return getNivel1(nivel1ctx);
    }

    private String getNivel1 (NoLanguageParser.OperadorNivel1Context ctx) {
        List<NoLanguageParser.OperadorNivel2Context> nivel2list = ctx.operadorNivel2();

        String newLine = getNivel2(nivel2list.get(0));

        for (int i = 1; i < nivel2list.size(); i++) {
            newLine += " " + ctx.NIVEL1().get(i-1).getText() + " ";
            newLine += getNivel2(nivel2list.get(i));
        }

        if (nivel2list.size() > 1)
            newLine = "(" + newLine + ")";

        return newLine;
    }

    private String getNivel2 (NoLanguageParser.OperadorNivel2Context ctx) {
        List<NoLanguageParser.TermoContext> termoList = ctx.termo();

        String newLine = getTermo(termoList.get(0));

        for (int i = 1; i < termoList.size(); i++) {
            newLine += " " + ctx.NIVEL2().get(i-1).getText() + " ";
            newLine += getTermo(termoList.get(i));
        }

        if (termoList.size() > 1)
            newLine = "(" + newLine + ")";

        return newLine;
    }

    private String getTermo (NoLanguageParser.TermoContext ctx) {
        if (ctx.NUMERO() != null)
            return ctx.NUMERO().getText();
        else if (ctx.ID() != null)
            return ctx.ID().getText();
        else
            return getExpressao(ctx.expressao());
    }

    private void addVar (String type, String name) {
        if (type.equals("int"))
            varInt.add(name);
        else
            varFloat.add(name);
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
