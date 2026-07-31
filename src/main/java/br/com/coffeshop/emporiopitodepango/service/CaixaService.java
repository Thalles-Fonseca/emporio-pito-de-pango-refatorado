package br.com.coffeshop.emporiopitodepango.service;

import br.com.coffeshop.emporiopitodepango.model.Caixa;
import br.com.coffeshop.emporiopitodepango.repository.CaixaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaixaService {

    private final CaixaRepository repository;

    public CaixaService(CaixaRepository repository) {
        this.repository = repository;
    }

    public int abrirRegistro(Caixa caixa) {
        validar(caixa);
        return repository.salvar(caixa);
    }

    public void atualizar(Caixa caixa) {
        validar(caixa);

        if (repository.buscarPorId(caixa.getIdCaixa()) == null) {
            throw new IllegalArgumentException("Registro de caixa não encontrado.");
        }

        repository.atualizar(caixa);
    }

    public void excluir(int idCaixa) {
        repository.excluir(idCaixa);
    }

    public List<Caixa> listarTodos() {
        return repository.listarTodos();
    }

    private void validar(Caixa caixa) {
        if (caixa == null) {
            throw new IllegalArgumentException("Registro de caixa não pode ser nulo.");
        }
        if (caixa.getOperador() == null || caixa.getOperador().trim().isEmpty()) {
            throw new IllegalArgumentException("Operador é obrigatório.");
        }
        if (caixa.getSaldoInicial() < 0) {
            throw new IllegalArgumentException("Saldo inicial não pode ser negativo.");
        }
    }
}
