package br.com.coffeshop.emporiopitodepango.repository;

import br.com.coffeshop.emporiopitodepango.model.Fornecedor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FornecedorRepository {

    public void salvar(Fornecedor f) {
        String sql = "INSERT INTO fornecedor (nome, cnpj, telefone, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCnpj());
            stmt.setString(3, f.getTelefone());
            stmt.setString(4, f.getEmail());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar fornecedor: " + e.getMessage(), e);
        }
    }

    public void atualizar(Fornecedor f) {
        String sql = "UPDATE fornecedor SET nome = ?, telefone = ?, email = ? WHERE cnpj = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getTelefone());
            stmt.setString(3, f.getEmail());
            stmt.setString(4, f.getCnpj());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar fornecedor: " + e.getMessage(), e);
        }
    }

    public void excluir(String cnpj) {
        String sql = "DELETE FROM fornecedor WHERE cnpj = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir fornecedor: " + e.getMessage(), e);
        }
    }

    public Fornecedor buscarPorCnpj(String cnpj) {
        String sql = "SELECT * FROM fornecedor WHERE cnpj = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar fornecedor: " + e.getMessage(), e);
        }

        return null;
    }

    public List<Fornecedor> listarTodos() {
        List<Fornecedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM fornecedor ORDER BY nome";

        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar fornecedores: " + e.getMessage(), e);
        }

        return lista;
    }

    private Fornecedor mapear(ResultSet rs) throws SQLException {
        return new Fornecedor(
            rs.getString("nome"),
            rs.getString("cnpj"),
            rs.getString("telefone"),
            rs.getString("email")
        );
    }
}
