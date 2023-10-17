package org.acme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class PedidoTest {

    private Pedido pedido;
    private List<ItemPedido> itens;
    private DescontoService descontoService;
    private DescontoService descontoService2;

    // Primeiro exercicio - 10% de desconto
    @Test
    void testCalcularValorTotalComDesconto10OFF() {
        descontoService = mock(DescontoService.class);
        when(descontoService.calcularDesconto(anyDouble())).thenReturn(35.0);

        itens = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 100.0, 2));
        itens.add(new ItemPedido("Item 2", 50.0, 3));
        pedido = new Pedido(itens, descontoService);

        double valorTotalComDesconto = pedido.calcularValorTotal();
        assertEquals(315.0, valorTotalComDesconto, 0.01);
    }

    // Segundo execicio - 0% de desconto
    @Test
    void testCalcularValorTotalComDesconto0OFF() {
        descontoService = mock(DescontoService.class);
        when(descontoService.calcularDesconto(anyDouble())).thenReturn(0.0);

        itens = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 100.0, 2));
        itens.add(new ItemPedido("Item 2", 50.0, 3));
        pedido = new Pedido(itens, descontoService);

        double valorTotalComDesconto = pedido.calcularValorTotal();
        assertEquals(350.0, valorTotalComDesconto, 0.01);
    }

    // Terceiro execicio - desconto maior que o valor total
    @Test
    void testCalcularValorTotalComDescontoMaiorQueValorTotal() {
        descontoService = mock(DescontoService.class);
        when(descontoService.calcularDesconto(anyDouble())).thenReturn(400.0);

        itens = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 100.0, 2));
        itens.add(new ItemPedido("Item 2", 50.0, 3));
        pedido = new Pedido(itens, descontoService);

        double valorTotalComDesconto = pedido.calcularValorTotal();
        assertEquals(0.0, valorTotalComDesconto, 0.01);
    }
    
    // Quarto exercicio - Retornar Exception
    @Test
    void testCalcularValorTotalComDescontoRetornandoException() {
        descontoService = mock(DescontoService.class);
        when(descontoService.calcularDesconto(anyDouble())).thenThrow(new IllegalArgumentException());

        itens = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 100.0, 2));
        itens.add(new ItemPedido("Item 2", 50.0, 3));
        pedido = new Pedido(itens, descontoService);

        assertThrows(IllegalArgumentException.class, () -> {
            descontoService.calcularDesconto(anyDouble());
        });
    }

    // Quinto exercicio - Lista vazia
    @Test
    void testCalcularValorTotalComDescontoListaVazia() {
        descontoService = mock(DescontoService.class);
        when(descontoService.calcularDesconto(anyDouble())).thenReturn(0.0);

        itens = new ArrayList<>();
        pedido = new Pedido(itens, descontoService);

        double valorTotalComDesconto = pedido.calcularValorTotal();
        assertEquals(0.0, valorTotalComDesconto, 0.01);
    }

    // Sexto exercicio - Lista vazia
    @Test
    void testCalcularValorTotalComDescontosDiferentes() {
        descontoService = mock(DescontoService.class);
        when(descontoService.calcularDesconto(anyDouble())).thenReturn(70.0); // Simula um desconto de 20%

        descontoService2 = mock(DescontoService.class);
        when(descontoService2.calcularDesconto(anyDouble())).thenReturn(35.0); // Simula um desconto de 10%

        itens = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 100.0, 2));
        itens.add(new ItemPedido("Item 2", 50.0, 3));

        pedido = new Pedido(itens, descontoService, descontoService2);

        double valorTotalComDesconto = pedido.calcularValorTotal();
        assertEquals(245.0, valorTotalComDesconto, 0.01);
    }

    // Sétimo exercicio - Chamar Calculo de desconto somente uma vez
    @Test
    void testCalcularValorTotalChamaCalcularDescontoUmaVez() {
        descontoService = mock(DescontoService.class);
        when(descontoService.calcularDesconto(anyDouble())).thenReturn(35.0);

        itens = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 100.0, 2));
        itens.add(new ItemPedido("Item 2", 50.0, 3));
        pedido = new Pedido(itens, descontoService);

        double valorTotalComDesconto = pedido.calcularValorTotal();
        assertEquals(1, pedido.getCalcularDescontoCalls());
    }
}
