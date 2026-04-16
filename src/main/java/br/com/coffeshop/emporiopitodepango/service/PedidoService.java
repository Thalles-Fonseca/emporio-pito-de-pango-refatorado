
package br.com.coffeshop.emporiopitodepango.service;
import br.com.coffeshop.emporiopitodepango.model.Pedido;
import br.com.coffeshop.emporiopitodepango.repository.PedidoRepository;
import java.util.List;

public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService() {
        this.repository = new PedidoRepository();
    }

    public void salvar(Pedido pedido) {
        validar(pedido);
        pedido.setTotal(calcularTotal(pedido));
        repository.salvar(pedido);
    }

    public void atualizar(Pedido pedido) {
        validar(pedido);
        pedido.setTotal(calcularTotal(pedido));
        repository.atualizar(pedido);
    }

    public List<Pedido> listarTodos() {
        return repository.listarTodos();
    }

    public Pedido buscarPorNumero(int numeroPedido) {
        if (numeroPedido <= 0) {
            throw new IllegalArgumentException("Número do pedido inválido.");
        }
        return repository.buscarPorNumero(numeroPedido);
    }

    public void excluir(int numeroPedido) {
        if (numeroPedido <= 0) {
            throw new IllegalArgumentException("Número do pedido inválido.");
        }
        repository.excluir(numeroPedido);
    }

    private void validar(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não pode ser nulo.");
        }
        if (pedido.getNumeroPedido() <= 0) {
            throw new IllegalArgumentException("Número do pedido inválido.");
        }
        if (pedido.getIdCliente() <= 0) {
            throw new IllegalArgumentException("Cliente inválido.");
        }
        if (pedido.getDataPedido() == null || pedido.getDataPedido().trim().isEmpty()) {
            throw new IllegalArgumentException("Data do pedido é obrigatória.");
        }
        if (pedido.getProduto() == null || pedido.getProduto().trim().isEmpty()) {
            throw new IllegalArgumentException("Produto é obrigatório.");
        }
        if (pedido.getValorUnitario() <= 0) {
            throw new IllegalArgumentException("Valor unitário deve ser maior que zero.");
        }
        if (pedido.getQuantidade() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
    }

    private double calcularTotal(Pedido pedido) {
        return pedido.getValorUnitario() * pedido.getQuantidade();
    }
}