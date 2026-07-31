package br.com.coffeshop.emporiopitodepango.service;

import br.com.coffeshop.emporiopitodepango.model.Usuario;
import br.com.coffeshop.emporiopitodepango.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Recebe a senha em texto puro (vinda do formulário), valida e grava
     * apenas o hash BCrypt no banco - a senha em texto puro nunca é
     * persistida nem logada.
     */
    public void cadastrar(Usuario usuario, String senhaEmTexto) {
        if (usuario == null || usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome de usuário é obrigatório.");
        }
        if (usuario.getPerfil() == null) {
            throw new IllegalArgumentException("Perfil é obrigatório.");
        }
        if (senhaEmTexto == null || senhaEmTexto.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter ao menos 6 caracteres.");
        }
        if (repository.buscarPorNome(usuario.getNome()) != null) {
            throw new IllegalArgumentException("Já existe um usuário com esse nome.");
        }

        usuario.setSenha(passwordEncoder.encode(senhaEmTexto));
        repository.salvar(usuario);
    }

    public void excluir(String nome) {
        repository.excluir(nome);
    }

    public List<Usuario> listarTodos() {
        return repository.listarTodos();
    }
}
