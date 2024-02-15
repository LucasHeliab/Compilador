/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiler;

/**
 *
 * @author heliab
 */
public class Token {
    public TiposToken nome;
    public String lexema;

    public Token(TiposToken nome, String lexema) {
        this.nome = nome;
        this.lexema = lexema;
    }

    @Override
    public String toString() {
        return "<" + nome + "," + lexema + ">";
    }
}