
package br.com.coffeshop.emporiopitodepango.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Antes, esta classe lia um arquivo "/application.properties" manualmente
 * procurando as chaves db.url/db.user/db.password - só que essas chaves
 * nunca existiram nesse arquivo (estavam em database.properties, que nunca
 * era carregado). Resultado: url/usuario/senha ficavam sempre nulos.
 *
 * Agora a classe é um @Component gerenciado pelo Spring, que injeta os
 * valores de spring.datasource.* já resolvendo ${DATABASE_URL} etc a partir
 * de variáveis de ambiente (produção) ou dos valores padrão definidos em
 * application.properties (desenvolvimento local). Guardamos os valores em
 * campos estáticos no @PostConstruct para que os repositories continuem
 * chamando ConexaoBD.getConnection() sem precisar de injeção de dependência
 * em cada um deles.
 */
@Component
public class ConexaoBD {

    @Value("${spring.datasource.url}")
    private String urlValue;

    @Value("${spring.datasource.username}")
    private String usuarioValue;

    @Value("${spring.datasource.password}")
    private String senhaValue;

    private static String url;
    private static String usuario;
    private static String senha;

    @PostConstruct
    public void init() {
        url = urlValue;
        usuario = usuarioValue;
        senha = senhaValue;
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco: " + e.getMessage(), e);
        }
    }
}