package org.acme;

import java.util.List;

public class Pedido {

    private List<ItemPedido> itens;
    private DescontoService descontoService;
    private DescontoService descontoService2;
    private int calcularDescontoCalls = 0;

    public Pedido(List<ItemPedido> itens, DescontoService descontoService) {
        this.itens = itens;
        this.descontoService = descontoService;
    }

    public Pedido(List<ItemPedido> itens, DescontoService descontoService, DescontoService descontoService2) {
        this.itens = itens;
        this.descontoService = descontoService;
        this.descontoService2 = descontoService2;
    }

    public double calcularValorTotal() {
        if (itens.isEmpty()) {
            return 0.0;
        }

        double valorTotal = 0.0;
        for (ItemPedido item : itens) {
            valorTotal += item.getSubtotal();
        }

        calcularDescontoCalls++; // Registra a chamada do método calcularDesconto.

        double desconto = descontoService.calcularDesconto(valorTotal);

        if (descontoService2 != null) {
            desconto += descontoService2.calcularDesconto(valorTotal);
        }

        if (valorTotal < desconto) {
            throw new IllegalArgumentException();
        }
        return valorTotal - desconto;
    }

    public int getCalcularDescontoCalls() {
        return calcularDescontoCalls;
    }
}
