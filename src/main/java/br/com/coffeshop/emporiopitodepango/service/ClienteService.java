
package br.com.coffeshop.emporiopitodepango.service;



import br.com.coffeshop.emporiopitodepango.model.Cliente;
import br.com.coffeshop.emporiopitodepango.repository.ClienteRepository;
import java.util.List;

public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService() {
        this.repository = new ClienteRepository();
    }

    public void salvar(Cliente cliente) {
        validar(cliente);

        if (repository.buscarPorCpf(cliente.getCpf()) != null) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        repository.salvar(cliente);
    }

    public void atualizar(Cliente cliente) {
        validar(cliente);

        if (repository.buscarPorCpf(cliente.getCpf()) == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }

        repository.atualizar(cliente);
    }

    public List<Cliente> listarTodos() {
        return repository.listarTodos();
    }

    public List<Cliente> buscar(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            throw new IllegalArgumentException("Termo de busca obrigatório.");
        }

        return repository.buscarPorNomeOuCpf(termo);
    }
    
    public Cliente buscarPorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF obrigatório.");
        }
        return repository.buscarPorCpf(cpf);
    }

    public void excluir(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF obrigatório.");
        }

        repository.excluir(cpf);
    }

    private void validar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório.");
        }
        if (cliente.getTelefone() == null || cliente.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone é obrigatório.");
        }
    }
}