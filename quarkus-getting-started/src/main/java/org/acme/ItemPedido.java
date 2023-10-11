
package org.acme;

public class ItemPedido {

    private String nome;
    private double precoUnitario;
    private int quantidade;

    public ItemPedido(String nome, double precoUnitario, int quantidade) {
        this.nome = nome;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return precoUnitario * quantidade;
    }
}
