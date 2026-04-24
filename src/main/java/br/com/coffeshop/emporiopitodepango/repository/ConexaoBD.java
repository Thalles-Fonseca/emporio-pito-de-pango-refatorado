
package br.com.coffeshop.emporiopitodepango.repository;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBD {

    private static final String ARQUIVO_CONFIG = "/application.properties";
    private static String url;
    private static String usuario;
    private static String senha;

    static {
        try (InputStream input = ConexaoBD.class.getResourceAsStream(ARQUIVO_CONFIG)) {
            if (input == null) {
                throw new RuntimeException("Arquivo database.properties não encontrado.");
            }

            Properties props = new Properties();
            props.load(input);

            url = props.getProperty("db.url");
            usuario = props.getProperty("db.user");
            senha = props.getProperty("db.password");

        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar as configurações do banco: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco: " + e.getMessage(), e);
        }
    }
}