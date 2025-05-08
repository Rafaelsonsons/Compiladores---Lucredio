package br.ufscar.dc.compiladores.alguma.sintatico;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import java.io.PrintWriter;

public class CustomErrorListener implements ANTLRErrorListener {
    PrintWriter pw;
    static boolean errorDetected = false;

    public CustomErrorListener(PrintWriter pw) {
        this.pw = pw;
    }

    @Override
    public void reportAmbiguity(Parser arg0, DFA arg1, int arg2, int arg3, boolean arg4, java.util.BitSet arg5, ATNConfigSet arg6) {}
    @Override
    public void reportAttemptingFullContext(Parser arg0, DFA arg1, int arg2, int arg3, java.util.BitSet arg4, ATNConfigSet arg5) {}
    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {}

    //Analise da sintaxe
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object o, int i, int i1, String s, RecognitionException e) {
        //token detectado
        Token t = (Token) o;
        String text = t.getText();
        //pegar o lexema
        String tipo = AlgumaLexer.VOCABULARY.getDisplayName(t.getType());
        //switch case para cada tipo
        if (!errorDetected) {
            switch (tipo) {
                case "ERRO":
                    pw.println("Linha " + t.getLine() + ": " + t.getText() + " - simbolo nao identificado");
                    errorDetected = true;
                    break;
                case "CADEIA_ABERTA":
                    pw.println("Linha " + t.getLine() + ": cadeia literal nao fechada");
                    errorDetected = true;
                    break;
                case "COMENTARIO_ABERTO":
                    pw.println("Linha " + t.getLine() + ": comentario nao fechado");
                    errorDetected = true;
                    break;
                default: //se for um erro sintatico
                    if (text.equals("<EOF>"))  text = "EOF";
                        pw.println("Linha " + t.getLine() + ": erro sintatico proximo a " + text);
                        errorDetected = true;
                        break;
            }
        }
    }
}
