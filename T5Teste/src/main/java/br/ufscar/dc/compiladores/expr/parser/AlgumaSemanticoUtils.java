package br.ufscar.dc.compiladores.expr.parser;

import br.ufscar.dc.compiladores.expr.parser.AlgumaParser;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

public class AlgumaSemanticoUtils {
    public static List<String> errosSemanticos = new ArrayList<>();

    public static void adicionarErroSemantico(Token t, String mensagem){
        int linha = t.getLine();

        errosSemanticos.add(String.format("Linha %d: %s", linha, mensagem));
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(Escopo escopo, AlgumaParser.ExpressaoContext ctx)  {
        TabelaDeSimbolos.TipoAlguma ret = null;
        for (var tl : ctx.termo_logico()) {
            TabelaDeSimbolos.TipoAlguma aux = verificarTipo(escopo, tl);

            if (ret == null)
            {
                ret = aux;
            }
            else if (ret != aux && aux != TabelaDeSimbolos.TipoAlguma.INVALIDO)
            {
                ret = TabelaDeSimbolos.TipoAlguma.INVALIDO;
            }
        }

        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(Escopo escopo, AlgumaParser.Termo_logicoContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        for (var fl : ctx.fator_logico()) {
            TabelaDeSimbolos.TipoAlguma aux = verificarTipo(escopo, fl);

            if (ret == null)
            {
                ret = aux;
            }
            else if (ret != aux && aux != TabelaDeSimbolos.TipoAlguma.INVALIDO)
            {
                ret = TabelaDeSimbolos.TipoAlguma.INVALIDO;
            }
        }

        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(Escopo escopo, AlgumaParser.Fator_logicoContext ctx){
        return verificarTipo(escopo, ctx.parcela_logica());
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(Escopo escopo, AlgumaParser.Parcela_logicaContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        if(ctx.exp_relacional() != null)
        {
            ret = verificarTipo(escopo, ctx.exp_relacional());
        }
        else
        {
            ret = TabelaDeSimbolos.TipoAlguma.LOGICO;
        }

        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(Escopo escopo, AlgumaParser.Exp_relacionalContext ctx){
        TabelaDeSimbolos.TipoAlguma ret = null;
        if(ctx.op_relacional() != null)
        {
            for(var ea : ctx.exp_aritmetica())
            {
                TabelaDeSimbolos.TipoAlguma aux = verificarTipo(escopo, ea);
                Boolean auxNumeric = aux == TabelaDeSimbolos.TipoAlguma.INTEIRO || aux == TabelaDeSimbolos.TipoAlguma.REAL;
                Boolean retNumeric = ret == TabelaDeSimbolos.TipoAlguma.INTEIRO || ret == TabelaDeSimbolos.TipoAlguma.REAL;

                if(ret == null)
                {
                    ret = aux;
                }
                else if(!(auxNumeric && retNumeric) && aux != ret)
                {
                    ret = TabelaDeSimbolos.TipoAlguma.INVALIDO;
                }
            }

            if(ret != TabelaDeSimbolos.TipoAlguma.INVALIDO)
            {
                ret = TabelaDeSimbolos.TipoAlguma.LOGICO;
            }
        }
        else
        {
            ret = verificarTipo(escopo, ctx.exp_aritmetica(0));
        }

        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(Escopo escopo, AlgumaParser.Exp_aritmeticaContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        for(var ta : ctx.termo())
        {
            TabelaDeSimbolos.TipoAlguma aux = verificarTipo(escopo, ta);

            if(ret == null)
            {
                ret = aux;
            }
            else if(ret != aux && aux != TabelaDeSimbolos.TipoAlguma.INVALIDO)
            {
                ret = TabelaDeSimbolos.TipoAlguma.INVALIDO;
            }
        }

        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(Escopo escopo, AlgumaParser.TermoContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        for(var fc : ctx.fator())
        {
            TabelaDeSimbolos.TipoAlguma aux = verificarTipo(escopo, fc);
            Boolean auxNumeric = aux == TabelaDeSimbolos.TipoAlguma.INTEIRO || aux == TabelaDeSimbolos.TipoAlguma.REAL;
            Boolean retNumeric = ret == TabelaDeSimbolos.TipoAlguma.INTEIRO || ret == TabelaDeSimbolos.TipoAlguma.REAL;

            if(ret == null)
            {
                ret = aux;
            }
            else if(!(auxNumeric && retNumeric) && aux != ret)
            {
                ret = TabelaDeSimbolos.TipoAlguma.INVALIDO;
            }
        }

        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(Escopo escopo, AlgumaParser.FatorContext ctx) {
        TabelaDeSimbolos.TipoAlguma ret = null;
        for(var pc : ctx.parcela())
        {
            TabelaDeSimbolos.TipoAlguma aux = verificarTipo(escopo, pc);

            if(ret == null)
            {
                ret = aux;
            }
            else if(ret != aux && aux != TabelaDeSimbolos.TipoAlguma.INVALIDO)
            {
                ret = TabelaDeSimbolos.TipoAlguma.INVALIDO;
            }
        }

        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(Escopo escopo, AlgumaParser.ParcelaContext ctx)  {
        TabelaDeSimbolos.TipoAlguma ret = null;
        if(ctx.parcela_nao_unario() != null)
        {
            ret = verificarTipo(escopo, ctx.parcela_nao_unario());
        }
        else
        {
            ret = verificarTipo(escopo, ctx.parcela_unario());
        }

        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(Escopo escopo,AlgumaParser.Parcela_nao_unarioContext ctx) {
        if(ctx.identificador() != null)
        {
            return verificarTipo(escopo, ctx.identificador());
        }

        return TabelaDeSimbolos.TipoAlguma.CADEIA;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(Escopo escopo, AlgumaParser.IdentificadorContext ctx) {
        String nomeVar = "";
        TabelaDeSimbolos.TipoAlguma ret = TabelaDeSimbolos.TipoAlguma.INVALIDO;

        for(int i = 0; i < ctx.IDENT().size(); i++)
        {
            nomeVar += ctx.IDENT(i).getText();
            if(i != ctx.IDENT().size() - 1)
            {
                nomeVar += ".";
            }
        }

        for(TabelaDeSimbolos tabela : escopo.getPilhaTabela()) {
            if(tabela.existe(nomeVar))
            {
                ret = verificarTipo(escopo, nomeVar);
            }
        }

        return ret;
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(Escopo escopo, AlgumaParser.Parcela_unarioContext ctx) {
        if(ctx.NUM_INT() != null)
            return TabelaDeSimbolos.TipoAlguma.INTEIRO; // Inteiro
        if(ctx.NUM_REAL() != null)
            return TabelaDeSimbolos.TipoAlguma.REAL; // Real
        if(ctx.identificador() != null)
            return verificarTipo(escopo, ctx.identificador());
        if (ctx.IDENT() != null)
            return verificarTipo(escopo, ctx.IDENT().getText());
        else{
            TabelaDeSimbolos.TipoAlguma ret = null;

            for(var ec : ctx.expressao())
            {
                TabelaDeSimbolos.TipoAlguma aux = verificarTipo(escopo, ec);

                if(ret == null)
                {
                    ret = aux;
                }
                else if (ret != aux && aux != TabelaDeSimbolos.TipoAlguma.INVALIDO)
                {
                    ret = TabelaDeSimbolos.TipoAlguma.INVALIDO;
                }
            }

            return ret;
        }
    }

    public static TabelaDeSimbolos.TipoAlguma verificarTipo(Escopo escopo, String nomeVar) {
        TabelaDeSimbolos.TipoAlguma type = TabelaDeSimbolos.TipoAlguma.INVALIDO;

        for(TabelaDeSimbolos tabela : escopo.getPilhaTabela())
        {
            if (tabela.existe(nomeVar))
            {
                return tabela.verificar(nomeVar);
            }
        }

        return type;
    }


    public static TabelaDeSimbolos.TipoAlguma getTipo(String tipo){
        switch (tipo){
            case "literal":
                return TabelaDeSimbolos.TipoAlguma.CADEIA;
            case "inteiro":
                return TabelaDeSimbolos.TipoAlguma.INTEIRO;
            case "real":
                return TabelaDeSimbolos.TipoAlguma.REAL;
            case "logico":
                return TabelaDeSimbolos.TipoAlguma.LOGICO;
            default:
                return null;
        }
    }
    // Usados para escrever em C
    public static String getTipoToC(String tipo){
        switch (tipo){
            case "literal":
                return "char";
            case "inteiro":
                return "int";
            case "real":
                return "float";
            default:
                return null;
        }
    }

    public static String getTipoAlgumToC(TabelaDeSimbolos.TipoAlguma tipo){
        switch (tipo){
            case INTEIRO: return "d";
            case REAL: return "f";
            case CADEIA: return "s";
            default: return null;
        }
    }
}