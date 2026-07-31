package br.com.coffeshop.emporiopitodepango.repository;

import br.com.coffeshop.emporiopitodepango.model.Caixa;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CaixaRepository {

    public int salvar(Caixa c) {
        String sql = "INSERT INTO caixa (data, operador, saldo_inicial, saldo_atual, descricao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, c.getData());
            stmt.setString(2, c.getOperador());
            stmt.setDouble(3, c.getSaldoInicial());
            stmt.setDouble(4, c.getSaldoAtual());
            stmt.setString(5, c.getDescricao());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar registro de caixa: " + e.getMessage(), e);
        }

        return -1;
    }

    public void atualizar(Caixa c) {
        String sql = "UPDATE caixa SET data = ?, operador = ?, saldo_inicial = ?, saldo_atual = ?, descricao = ? WHERE id_caixa = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getData());
            stmt.setString(2, c.getOperador());
            stmt.setDouble(3, c.getSaldoInicial());
            stmt.setDouble(4, c.getSaldoAtual());
            stmt.setString(5, c.getDescricao());
            stmt.setInt(6, c.getIdCaixa());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar registro de caixa: " + e.getMessage(), e);
        }
    }

    public void excluir(int idCaixa) {
        String sql = "DELETE FROM caixa WHERE id_caixa = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCaixa);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir registro de caixa: " + e.getMessage(), e);
        }
    }

    public Caixa buscarPorId(int idCaixa) {
        String sql = "SELECT * FROM caixa WHERE id_caixa = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCaixa);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar registro de caixa: " + e.getMessage(), e);
        }

        return null;
    }

    public List<Caixa> listarTodos() {
        List<Caixa> lista = new ArrayList<>();
        String sql = "SELECT * FROM caixa ORDER BY id_caixa DESC";

        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar registros de caixa: " + e.getMessage(), e);
        }

        return lista;
    }

    private Caixa mapear(ResultSet rs) throws SQLException {
        return new Caixa(
            rs.getInt("id_caixa"),
            rs.getString("data"),
            rs.getString("operador"),
            rs.getDouble("saldo_inicial"),
            rs.getDouble("saldo_atual"),
            rs.getString("descricao")
        );
    }
}
