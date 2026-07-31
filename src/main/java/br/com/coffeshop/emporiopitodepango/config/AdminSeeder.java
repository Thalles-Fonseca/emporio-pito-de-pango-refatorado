package br.com.coffeshop.emporiopitodepango.config;

import br.com.coffeshop.emporiopitodepango.model.Usuario;
import br.com.coffeshop.emporiopitodepango.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Sem isso, ninguém conseguiria logar no painel pela primeira vez (a tabela
 * "usuario" nasce vazia). Ao subir a aplicação, se não existir NENHUM
 * usuário, criamos um GERENTE temporário e avisamos no log para trocar a
 * senha assim que possível (idealmente: criar outro GERENTE pelo próprio
 * painel e excluir este "admin").
 */
@Component
public class AdminSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminSeeder.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.contar() == 0) {
            Usuario admin = new Usuario("admin", passwordEncoder.encode("troque123"), Usuario.Perfil.GERENTE);
            usuarioRepository.salvar(admin);
            log.warn("Nenhum usuário encontrado no banco. Usuário 'admin' criado com senha "
                + "temporária 'troque123'. Faça login, crie um usuário GERENTE definitivo pelo "
                + "painel e EXCLUA este 'admin' em seguida.");
        }
    }
}
