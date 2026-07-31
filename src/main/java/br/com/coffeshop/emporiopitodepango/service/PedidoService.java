package br.com.coffeshop.emporiopitodepango.service;

import br.com.coffeshop.emporiopitodepango.model.Pedido;
import br.com.coffeshop.emporiopitodepango.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final CalculadoraPedidoService calculadoraPedidoService;

    public PedidoService(PedidoRepository repository, CalculadoraPedidoService calculadoraPedidoService) {
        this.repository = repository;
        this.calculadoraPedidoService = calculadoraPedidoService;
    }

    /**
     * Cria um novo pedido. numeroPedido eh gerado pelo banco (AUTO_INCREMENT) -
     * retorna o numero gerado para o chamador poder mostrar/usar.
     */
    public int salvar(Pedido pedido) {
        validarCampos(pedido);
        pedido.setTotal(calculadoraPedidoService.calcularTotal(
                pedido.getValorUnitario(),
                pedido.getQuantidade()
        ));
        return repository.salvar(pedido);
    }

    public void atualizar(Pedido pedido) {
        validarCampos(pedido);
        if (pedido.getNumeroPedido() <= 0) {
            throw new IllegalArgumentException("Número do pedido inválido.");
        }
        pedido.setTotal(calculadoraPedidoService.calcularTotal(
                pedido.getValorUnitario(),
                pedido.getQuantidade()
        ));
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

    private void validarCampos(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não pode ser nulo.");
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
}
