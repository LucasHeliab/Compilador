/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package compiler;

/**
 *
 * @author heliab
 */
public class Compiler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AnalisadorLexico lex = new AnalisadorLexico(args[0]);
        Token token = null;

        while ((token = lex.proximoToken()).nome != TiposToken.Fim) {
            System.out.println(token);
        }
    }   
    
}
