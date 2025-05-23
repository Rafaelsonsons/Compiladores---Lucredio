package br.ufscar.dc.compiladores.alguma.sintatico;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

public class Principal {
    public static void main(String args[]) throws IOException {

        String arquivoSaida = args[1];

        try(PrintWriter pw = new PrintWriter(arquivoSaida)) {
            CharStream cs = CharStreams.fromFileName(args[0]);
            AlgumaLexer lexer = new AlgumaLexer(cs);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            AlgumaParser parser = new AlgumaParser(tokens);

            //modificacao do analisador de erro
            CustomErrorListener errorListener = new CustomErrorListener(pw);
            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);

            parser.programa(); //ativacao do parser

            pw.println("Fim da compilacao");
        } catch(FileNotFoundException fnfe) {
            System.err.println("O arquivo/diretório não existe:"+args[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
