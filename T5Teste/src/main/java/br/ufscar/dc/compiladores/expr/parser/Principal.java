package br.ufscar.dc.compiladores.expr.parser;
import br.ufscar.dc.compiladores.expr.parser.AlgumaParser.ProgramaContext;

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

            ProgramaContext arvore = parser.programa();

            AlgumaSemantico as = new AlgumaSemantico();

            as.visitPrograma(arvore);

            if (!AlgumaSemanticoUtils.errosSemanticos.isEmpty())
            {
                for (String error : AlgumaSemanticoUtils.errosSemanticos)
                {
                    pw.println(error);
                }

                pw.close();
                return;
            }
            pw.close();

            // ESCRITA DO CÓDIGO EM C
            AlgumaToC converter = new AlgumaToC();
            converter.visitPrograma(arvore);

            PrintWriter cWriter = new PrintWriter(args[1]);
            cWriter.print(converter.cOutput.toString());
            cWriter.close();

        } catch(FileNotFoundException fnfe) {
            System.err.println("O arquivo/diretório não existe:"+args[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}