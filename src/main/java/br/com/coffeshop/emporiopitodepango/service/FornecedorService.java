package br.com.coffeshop.emporiopitodepango.service;

import br.com.coffeshop.emporiopitodepango.model.Fornecedor;
import br.com.coffeshop.emporiopitodepango.repository.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorService {

    private final FornecedorRepository repository;

    public FornecedorService(FornecedorRepository repository) {
        this.repository = repository;
    }

    public void salvar(Fornecedor fornecedor) {
        validar(fornecedor);

        if (repository.buscarPorCnpj(fornecedor.getCnpj()) != null) {
            throw new IllegalArgumentException("Já existe fornecedor com esse CNPJ.");
        }

        repository.salvar(fornecedor);
    }

    public void atualizar(Fornecedor fornecedor) {
        validar(fornecedor);

        if (repository.buscarPorCnpj(fornecedor.getCnpj()) == null) {
            throw new IllegalArgumentException("Fornecedor não encontrado para atualização.");
        }

        repository.atualizar(fornecedor);
    }

    public void excluir(String cnpj) {
        repository.excluir(cnpj);
    }

    public Fornecedor buscarPorCnpj(String cnpj) {
        return repository.buscarPorCnpj(cnpj);
    }

    public List<Fornecedor> listarTodos() {
        return repository.listarTodos();
    }

    private void validar(Fornecedor fornecedor) {
        if (fornecedor == null) {
            throw new IllegalArgumentException("Fornecedor não pode ser nulo.");
        }
        if (fornecedor.getNome() == null || fornecedor.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do fornecedor é obrigatório.");
        }
        if (fornecedor.getCnpj() == null || fornecedor.getCnpj().trim().isEmpty()) {
            throw new IllegalArgumentException("CNPJ é obrigatório.");
        }
    }
}
