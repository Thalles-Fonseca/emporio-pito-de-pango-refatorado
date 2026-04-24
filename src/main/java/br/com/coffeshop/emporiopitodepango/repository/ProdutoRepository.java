
package br.com.coffeshop.emporiopitodepango.repository;


import br.com.coffeshop.emporiopitodepango.model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoRepository {

    public void salvar(Produto p) {
        String sql = "INSERT INTO produto (codigo, nome, fornecedor, quantidade, valor, data_cadastro, descricao, categoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getCodigo());
            stmt.setString(2, p.getNome());
            stmt.setString(3, p.getFornecedor());
            stmt.setInt(4, p.getQuantidade());
            stmt.setDouble(5, p.getValor());
            stmt.setString(6, p.getDataCadastro());
            stmt.setString(7, p.getDescricao());
            stmt.setString(8, p.getCategoria());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto: " + e.getMessage(), e);
        }
    }

    public void atualizar(Produto p) {
        String sql = "UPDATE produto SET nome=?, fornecedor=?, quantidade=?, valor=?, data_cadastro=?, descricao=?, categoria=? WHERE codigo=?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getFornecedor());
            stmt.setInt(3, p.getQuantidade());
            stmt.setDouble(4, p.getValor());
            stmt.setString(5, p.getDataCadastro());
            stmt.setString(6, p.getDescricao());
            stmt.setString(7, p.getCategoria());
            stmt.setInt(8, p.getCodigo());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }

    public void excluir(int codigo) {
        String sql = "DELETE FROM produto WHERE codigo=?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir produto: " + e.getMessage(), e);
        }
    }

    public List<Produto> listarTodos() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto";

        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto p = new Produto(
                    rs.getInt("codigo"),
                    rs.getString("nome"),
                    rs.getString("fornecedor"),
                    rs.getInt("quantidade"),
                    rs.getString("data_cadastro"),
                    rs.getString("descricao"),
                    rs.getDouble("valor"),
                    rs.getString("categoria")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage(), e);
        }
        return lista;
    }

    public Produto buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM produto WHERE codigo = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Produto(
                    rs.getInt("codigo"),
                    rs.getString("nome"),
                    rs.getString("fornecedor"),
                    rs.getInt("quantidade"),
                    rs.getString("data_cadastro"),
                    rs.getString("descricao"),
                    rs.getDouble("valor"),
                    rs.getString("categoria")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto por código: " + e.getMessage(), e);
        }

        return null;
    }

    public List<Produto> buscarPorNome(String nome) {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE nome LIKE ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto p = new Produto(
                    rs.getInt("codigo"),
                    rs.getString("nome"),
                    rs.getString("fornecedor"),
                    rs.getInt("quantidade"),
                    rs.getString("data_cadastro"),
                    rs.getString("descricao"),
                    rs.getDouble("valor"),
                    rs.getString("categoria")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto por nome: " + e.getMessage(), e);
        }

        return lista;
    }
}