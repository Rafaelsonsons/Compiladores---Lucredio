package br.ufscar.dc.compiladores.T1Teste;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

public class App 
{
    public static void main(String[] args) {
        String arquivoSaida = args[1];
        try(PrintWriter pw = new PrintWriter(arquivoSaida)) {

            CharStream cs = CharStreams.fromFileName(args[0]);
            T1Lexer lex = new T1Lexer(cs);

            Token t = null;


            while ((t = lex.nextToken()).getType() != Token.EOF) {
                switch (T1Lexer.VOCABULARY.getDisplayName(t.getType())){
                    case "PALAVRA_CHAVE":
                    case "OP_ARIT":
                    case "OP_REL":
                    case "OP_ACESSO":
                    case "OP_ATR":
                        pw.println("<'" + t.getText() + "','" + t.getText() + "'>");
                        break;
                    case "ERRO":
                        pw.println("Linha " + t.getLine() + ": " + t.getText() + " - simbolo nao identificado" );
                        return;
                    case "CADEIA_ABERTA":
                        pw.println("Linha " + t.getLine() + ": cadeia literal nao fechada" );
                        return;
                    case "COMENTARIO_ABERTO":
                        pw.println("Linha " + t.getLine() + ": comentario nao fechado" );
                        return;
                    default:
                        pw.println("<'" + t.getText() + "'," + T1Lexer.VOCABULARY.getDisplayName(t.getType()) + ">");
                        break;
                }
            }

        } catch(FileNotFoundException fnfe) {
            System.err.println("O arquivo/diretório não existe:"+args[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
