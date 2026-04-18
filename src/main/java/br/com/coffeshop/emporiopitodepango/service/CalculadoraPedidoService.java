
package br.com.coffeshop.emporiopitodepango.service;


public class CalculadoraPedidoService {

    public double calcularTotal(double valorUnitario, int quantidade) {
        if (valorUnitario <= 0) {
            throw new IllegalArgumentException("Valor unitário deve ser maior que zero.");
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }

        return valorUnitario * quantidade;
    }
}