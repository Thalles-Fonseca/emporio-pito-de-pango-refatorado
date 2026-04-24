package br.com.coffeshop.emporiopitodepango.main;

import br.com.coffeshop.emporiopitodepango.model.Cliente;
import br.com.coffeshop.emporiopitodepango.model.Pedido;
import br.com.coffeshop.emporiopitodepango.model.Produto;
import br.com.coffeshop.emporiopitodepango.service.ClienteService;
import br.com.coffeshop.emporiopitodepango.service.PedidoService;
import br.com.coffeshop.emporiopitodepango.service.ProdutoService;


public class EmporioPitoDePango {

    public static void main(String[] args) {
        ProdutoService produtoService = new ProdutoService();
        ClienteService clienteService = new ClienteService();
        PedidoService pedidoService = new PedidoService();

        try {
            System.out.println("=== TESTE DE PRODUTO ===");
            Produto produto = new Produto(102, "Café Expresso", "Fornecedor A", 20,
                    "2026-04-13", "Café Especial", 9.50, "Bebida");
            produtoService.salvar(produto);
            System.out.println("Produto salvo com sucesso.");

            for (Produto p : produtoService.listarTodos()) {
                System.out.println("Produto: " + p.getCodigo() + " - " + p.getNome());
            }

            System.out.println("\n=== TESTE DE CLIENTE ===");
            Cliente cliente = new Cliente("João Silva da Rocha", "12345678910", "71999999998",
                    "joaoR@email.com", "Rua B, 10");
            clienteService.salvar(cliente);
            System.out.println("Cliente salvo com sucesso.");

            for (Cliente c : clienteService.listarTodos()) {
                System.out.println("Cliente: " + c.getId() + " - " + c.getNome() + " - " + c.getCpf());
            }

            Cliente clienteBanco = clienteService.buscar("12345678900").get(0);

            System.out.println("\n=== TESTE DE PEDIDO ===");
            Pedido pedido = new Pedido(1, clienteBanco.getId(), clienteBanco.getNome(),
                    "2026-04-13", "Café Expresso", 7.50, 2);
            pedidoService.salvar(pedido);
            System.out.println("Pedido salvo com sucesso.");

            for (Pedido p : pedidoService.listarTodos()) {
                System.out.println("Pedido: " + p.getNumeroPedido()
                        + " | Cliente: " + p.getNomeCliente()
                        + " | Produto: " + p.getProduto()
                        + " | Total: " + p.getTotal());
            }

            System.out.println("\nTodos os testes executados com sucesso.");

        } catch (Exception e) {
            System.out.println("Erro durante os testes: " + e.getMessage());
        }
    }
}