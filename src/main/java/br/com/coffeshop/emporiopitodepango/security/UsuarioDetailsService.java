package br.com.coffeshop.emporiopitodepango.security;

import br.com.coffeshop.emporiopitodepango.model.Usuario;
import br.com.coffeshop.emporiopitodepango.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Ponte entre o Spring Security e a tabela "usuario" já usada pelo sistema
 * desktop. O perfil (GERENTE/FINANCEIRO/ATENDENTE) vira uma "role" do Spring
 * Security, o que permite usar hasRole()/hasAnyRole() nas rotas e sec:authorize
 * nos templates.
 */
@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.buscarPorNome(nome);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + nome);
        }

        return User.builder()
            .username(usuario.getNome())
            .password(usuario.getSenha())
            .roles(usuario.getPerfil().name())
            .build();
    }
}
