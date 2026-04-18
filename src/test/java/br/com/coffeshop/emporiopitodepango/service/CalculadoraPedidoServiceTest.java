package br.com.coffeshop.emporiopitodepango.service;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraPedidoServiceTest {

    @Test
    public void deveCalcularTotalCorretamente() {
        CalculadoraPedidoService calculadora = new CalculadoraPedidoService();

        double resultado = calculadora.calcularTotal(10.0, 3);

        assertEquals(30.0, resultado);
    }

    @Test
    public void deveLancarExcecaoQuandoValorUnitarioForInvalido() {
        CalculadoraPedidoService calculadora = new CalculadoraPedidoService();

        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.calcularTotal(0, 3)
        );

        assertEquals("Valor unitário deve ser maior que zero.", excecao.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoQuantidadeForInvalida() {
        CalculadoraPedidoService calculadora = new CalculadoraPedidoService();

        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.calcularTotal(10.0, 0)
        );

        assertEquals("Quantidade deve ser maior que zero.", excecao.getMessage());
    }
}