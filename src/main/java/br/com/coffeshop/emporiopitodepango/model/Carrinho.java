package br.com.coffeshop.emporiopitodepango.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Carrinho de compras guardado na sessão HTTP - um por visitante. Não
 * precisa de login nem de tabela no banco: some quando a sessão expira.
 * O pedido só vira registro persistido no banco quando o checkout é
 * finalizado (CarrinhoController).
 */
@Component
@SessionScope
public class Carrinho {

    private final Map<Integer, ItemCarrinho> itens = new LinkedHashMap<>();

    public void adicionar(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }

        ItemCarrinho existente = itens.get(produto.getCodigo());
        int novaQuantidade = (existente != null ? existente.getQuantidade() : 0) + quantidade;

        if (novaQuantidade > produto.getQuantidade()) {
            throw new IllegalArgumentException("Quantidade solicitada maior que o estoque disponível.");
        }

        if (existente != null) {
            existente.setQuantidade(novaQuantidade);
        } else {
            itens.put(produto.getCodigo(), new ItemCarrinho(
                produto.getCodigo(), produto.getNome(), produto.getValor(), quantidade
            ));
        }
    }

    public void alterarQuantidade(int codigoProduto, int quantidade) {
        ItemCarrinho item = itens.get(codigoProduto);
        if (item == null) {
            return;
        }
        if (quantidade <= 0) {
            itens.remove(codigoProduto);
            return;
        }
        item.setQuantidade(quantidade);
    }

    public void remover(int codigoProduto) {
        itens.remove(codigoProduto);
    }

    public void limpar() {
        itens.clear();
    }

    public List<ItemCarrinho> getItens() {
        return new ArrayList<>(itens.values());
    }

    public boolean isVazio() {
        return itens.isEmpty();
    }

    public double getTotal() {
        return itens.values().stream().mapToDouble(ItemCarrinho::getSubtotal).sum();
    }

    public int getQuantidadeTotalItens() {
        return itens.values().stream().mapToInt(ItemCarrinho::getQuantidade).sum();
    }
}
