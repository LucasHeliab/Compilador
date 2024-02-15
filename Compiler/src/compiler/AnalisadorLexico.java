/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler;

/**
 *
 * @author heliab
 */
public class AnalisadorLexico {

    LeitorDeArquivoDeTexto leitor;

    public AnalisadorLexico(String arquivo) {
        leitor = new LeitorDeArquivoDeTexto(arquivo);
    }

    public Token proximoToken() {
        Token proximo = null;

        espacosEComentarios();
        leitor.confirmar();

        proximo = fim();
        if (proximo == null) {
            leitor.zerar();
        } else {
            leitor.confirmar();
            return proximo;
        }

        proximo = palavrasChave();
        if (proximo == null) {
            leitor.zerar();
        } else {
            leitor.confirmar();
            return proximo;
        }

        proximo = variavel();
        if (proximo == null) {
            leitor.zerar();
        } else {
            leitor.confirmar();
            return proximo;
        }
        
        proximo = numeros();
        if (proximo == null) {
            leitor.zerar();
        } else {
            leitor.confirmar();
            return proximo;
        }

        proximo = operadorAritmetico();
        if (proximo == null) {
            leitor.zerar();
        } else {
            leitor.confirmar();
            return proximo;
        }
        
        proximo = operadorRelacional();
        if (proximo == null) {
            leitor.zerar();
        } else {
            leitor.confirmar();
            return proximo;
        }
        
        proximo = delimitador();
        if (proximo == null) {
            leitor.zerar();
        } else {
            leitor.confirmar();
            return proximo;
        }
        
        proximo = parenteses();
        if (proximo == null) {
            leitor.zerar();
        } else {
            leitor.confirmar();
            return proximo;
        }   
        
        proximo = cadeia();
        if (proximo == null) {
            leitor.zerar();
        } else {
            leitor.confirmar();
            return proximo;
        }   
        
        System.err.println("Erro léxico!");
        System.err.println(leitor.toString());
        return null;
    }

    private void espacosEComentarios() {
        int estado = 1;
        while (true) {
            char caractere = (char) leitor.lerProximoCaractere();
            if (estado == 1) {
                if (Character.isWhitespace(caractere) || caractere == ' ') {
                    estado = 2;
                } else if (caractere == '%') {
                    estado = 3;
                } else {
                    leitor.retroceder();
                    return;
                }
            } else if (estado == 2) {
                if (caractere == '%') {
                    estado = 3;
                } else if (!(Character.isWhitespace(caractere) || caractere == ' ')) {
                    leitor.retroceder();
                    return;
                }
            } else if (estado == 3) {
                if (caractere == '\n') {
                    return;
                }
            }
        }
    }

    private Token palavrasChave() {
        while (true) {
            char caractere = (char) leitor.lerProximoCaractere();
            if (!Character.isLetter(caractere)) {
                leitor.retroceder();
                String lexema = leitor.getLexema();
                if (lexema.equals("DECLARACOES")) {
                    return new Token(TiposToken.PCDeclaracoes, lexema);
                } else if (lexema.equals("ALGORITMO")) {
                    return new Token(TiposToken.PCAlgoritmo, lexema);
                } else if (lexema.equals("INTEIRO")) {
                    return new Token(TiposToken.PCInteiro, lexema);
                } else if (lexema.equals("REAL")) {
                    return new Token(TiposToken.PCReal, lexema);
                } else if (lexema.equals("ATRIBUIR")) {
                    return new Token(TiposToken.PCAtribuir, lexema);
                } else if (lexema.equals("A")) {
                    return new Token(TiposToken.PCA, lexema);
                } else if (lexema.equals("LER")) {
                    return new Token(TiposToken.PCLer, lexema);
                } else if (lexema.equals("IMPRIMIR")) {
                    return new Token(TiposToken.PCImprimir, lexema);
                } else if (lexema.equals("SE")) {
                    return new Token(TiposToken.PCSe, lexema);
                } else if (lexema.equals("ENTAO")) {
                    return new Token(TiposToken.PCEntao, lexema);
                } else if (lexema.equals("ENQUANTO")) {
                    return new Token(TiposToken.PCEnquanto, lexema);
                } else if (lexema.equals("INICIO")) {
                    return new Token(TiposToken.PCInicio, lexema);
                } else if (lexema.equals("FIM")) {
                    return new Token(TiposToken.PCFim, lexema);
                } else if (lexema.equals("E")) {
                    return new Token(TiposToken.OpBoolE, lexema);
                } else if (lexema.equals("OU")) {
                    return new Token(TiposToken.OpBoolOu, lexema);
                } else {
                    return null;
                }
            }
        }
    }

    private Token variavel() {
        int estado = 1;
        while (true) {
            char caractere = (char) leitor.lerProximoCaractere();
            if (estado == 1) {
                if (Character.isLetter(caractere)) {
                    estado = 2;
                } else {
                    return null;
                }
            } else if (estado == 2) {
                if (!Character.isLetterOrDigit(caractere)) {
                    leitor.retroceder();
                    return new Token(TiposToken.Var, leitor.getLexema());
                }
            }
        }
    }

    private Token numeros() {
        int estado = 1;
        while (true) {
            char caractere = (char) leitor.lerProximoCaractere();
            if (estado == 1) {
                if (Character.isDigit(caractere)) {
                    estado = 2;
                } else {
                    return null;
                }
            } else if (estado == 2) {
                if (caractere == '.') {
                    caractere = (char) leitor.lerProximoCaractere();
                    if (Character.isDigit(caractere)) {
                        estado = 3;
                    } else {
                        return null;
                    }

                } else if (!Character.isDigit(caractere)) {
                    leitor.retroceder();
                    return new Token(TiposToken.NumInt, leitor.getLexema());
                }
            } else if (estado == 3) {
                if (!Character.isDigit(caractere)) {
                    leitor.retroceder();
                    return new Token(TiposToken.NumReal, leitor.getLexema());
                }
            }

        }
    }

    private Token operadorAritmetico() {
        int caractereLido = leitor.lerProximoCaractere();
        char caractere = (char) caractereLido;
        if (caractere == '*') {
            return new Token(TiposToken.OpAritMult, "*");
        } else if (caractere == '/') {
            return new Token(TiposToken.OpAritDiv, "/");
        } else if (caractere == '+') {
            return new Token(TiposToken.OpAritSoma, "+");
        } else if (caractere == '-') {
            return new Token(TiposToken.OpAritSub, "-");
        } else {
            return null;
        }
    }
    
    private Token operadorRelacional() {
        int caractereLido = leitor.lerProximoCaractere();
        char caractere = (char) caractereLido;
        if (caractere == '<') {
            caractere = (char) leitor.lerProximoCaractere();
            switch (caractere) {
                case '>':
                    return new Token(TiposToken.OpRelDif, "<>");
                case '=':
                    return new Token(TiposToken.OpRelMenorIgual, "<=");
                default:
                    leitor.retroceder();
                    return new Token(TiposToken.OpRelMenor, "<");
            }
        } else if (caractere == '=') {
            return new Token(TiposToken.OpRelIgual, "=");
        } else if (caractere == '>') {
            caractere = (char) leitor.lerProximoCaractere();
            if (caractere == '=') {
                return new Token(TiposToken.OpRelMaiorIgual, ">=");
            } else {
                leitor.retroceder();
                return new Token(TiposToken.OpRelMaior, ">");
            }
        } else {
            return null;
        }
    }

    private Token delimitador() {
        int caractereLido = leitor.lerProximoCaractere();
        char caractere = (char) caractereLido;
        if (caractere == ':') {
            return new Token(TiposToken.Delim, ":");
        } else {
            return null;
        }
    }

    private Token parenteses() {
        int caractereLido = leitor.lerProximoCaractere();
        char caractere = (char) caractereLido;
        if (caractere == '(') {
            return new Token(TiposToken.AbrePar, "(");
        } else if (caractere == ')') {
            return new Token(TiposToken.FechaPar, ")");
        } else {
            return null;
        }
    }

    
    private Token cadeia() {
        int estado = 1;
        while (true) {
            char caractere = (char) leitor.lerProximoCaractere();
            if (estado == 1) {
                if (caractere == '\'') {
                    estado = 2;
                } else {
                    return null;
                }
            } else if (estado == 2) {
                if (caractere == '\n') {
                    return null;
                }

                if (caractere == '\'') {
                    return new Token(TiposToken.Cadeia, leitor.getLexema());
                } else if (caractere == '\\') {
                    estado = 3;
                }

            } else if (estado == 3) {
                if (caractere == '\n') {
                    return null;
                } else {
                    estado = 2;
                }
            }
        }
    }

    private Token fim() {
        int caractereLido = leitor.lerProximoCaractere();
        if (caractereLido == -1) {
            return new Token(TiposToken.Fim, "Fim");
        }
        return null;
    }
}
