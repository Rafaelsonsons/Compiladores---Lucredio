package br.ufscar.dc.compiladores.expr.parser;


import br.ufscar.dc.compiladores.expr.parser.AlgumaParser;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;




public class AlgumaSemanticoUtils {
    public static List<String> errosSemanticos = new ArrayList<>();

    public static void adicionarErroSemantico(Token t, String mensagem) {
        int linha = t.getLine();
        int coluna = t.getCharPositionInLine();
        errosSemanticos.add(String.format("Erro %d:%d - %s", linha, coluna, mensagem));
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, AlgumaParser.ExpressaoAritmeticaContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        for (var ta : ctx.termoAritmetico()) {
            TabelaDeSimbolos.TipoAlguma aux = verificarTipo(tabela, ta);
            if (ret == null) {
                ret = aux;
            } else if (ret != aux && aux != TabelaDeSimbolos.TipoAlguma.INVALIDO) {
                adicionarErroSemantico(ctx.start, "Expressão " + ctx.getText() + " contém tipos incompatíveis");
                ret = TabelaDeSimbolos.TipoAlguma.INVALIDO;
            }
        }

        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, AlgumaParser.TermoAritmeticoContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;

        for (var fa : ctx.fatorAritmetico()) {
            TabelaDeSimbolos.TipoAlguma aux = verificarTipo(tabela, fa);
            if (ret == null) {
                ret = aux;
            } else if (ret != aux && aux != TabelaDeSimbolos.TipoAlguma.INVALIDO) {
                adicionarErroSemantico(ctx.start, "Termo " + ctx.getText() + " contém tipos incompatíveis");
                ret = TabelaDeSimbolos.TipoAlguma.INVALIDO;
            }
        }
        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, AlgumaParser.FatorAritmeticoContext ctx) {
        if (ctx.NUMINT() != null) {
            return TabelaDeSimbolos.TipoAlguma.INTEIRO;
        }
        if (ctx.NUMREAL() != null) {
            return TabelaDeSimbolos.TipoAlguma.REAL;
        }
        if (ctx.VARIAVEL() != null) {
            String nomeVar = ctx.VARIAVEL().getText();
            if (!tabela.existe(nomeVar)) {
                adicionarErroSemantico(ctx.VARIAVEL().getSymbol(), "Variável " + nomeVar + " não foi declarada antes do uso");
                return TabelaDeSimbolos.TipoAlguma.INVALIDO;
            }
            return verificarTipo(tabela, nomeVar);
        }
        // se não for nenhum dos tipos acima, só pode ser uma expressão
        // entre parêntesis
        return verificarTipo(tabela, ctx.expressaoAritmetica());
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, String nomeVar) {
        return tabela.verificar(nomeVar);
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, AlgumaParser.ExpressaoContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, AlgumaParser.Fator_logicoContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, AlgumaParser.Parcela_logicaContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, AlgumaParser.Termo_logicoContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, AlgumaParser.Exp_relacionalContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, AlgumaParser.Exp_aritmeticaContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, AlgumaParser.TermoContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, AlgumaParser.FatorContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, AlgumaParser.ParcelaContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, AlgumaParser.Parcela_nao_unarioContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, AlgumaParser.IdentificadorContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, AlgumaParser.Parcela_unarioContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        return ret;
    }

}