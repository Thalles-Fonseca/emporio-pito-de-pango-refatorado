package br.com.coffeshop.emporiopitodepango.service;

import br.com.coffeshop.emporiopitodepango.model.Produto;
import br.com.coffeshop.emporiopitodepango.repository.ProdutoRepository;
import java.util.List;


public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService() {
        this.repository = new ProdutoRepository();
    }

    public void salvar(Produto produto) {
        validarProduto(produto);

        if (repository.buscarPorCodigo(produto.getCodigo()) != null) {
            throw new IllegalArgumentException("Já existe produto com esse código.");
        }

        repository.salvar(produto);
    }

    public void atualizar(Produto produto) {
        validarProduto(produto);

        if (repository.buscarPorCodigo(produto.getCodigo()) == null) {
            throw new IllegalArgumentException("Produto não encontrado para atualização.");
        }

        repository.atualizar(produto);
    }

    public void excluir(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("Código inválido.");
        }

        repository.excluir(codigo);
    }

    public List<Produto> listarTodos() {
        return repository.listarTodos();
    }

    public List<Produto> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome para busca é obrigatório.");
        }

        return repository.buscarPorNome(nome);
    }

    private void validarProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo.");
        }
        if (produto.getCodigo() <= 0) {
            throw new IllegalArgumentException("Código do produto deve ser maior que zero.");
        }
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório.");
        }
        if (produto.getFornecedor() == null || produto.getFornecedor().trim().isEmpty()) {
            throw new IllegalArgumentException("Fornecedor é obrigatório.");
        }
        if (produto.getQuantidade() < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa.");
        }
        if (produto.getValor() < 0) {
            throw new IllegalArgumentException("Valor não pode ser negativo.");
        }
        if (produto.getCategoria() == null || produto.getCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria é obrigatória.");
        }
    }
}