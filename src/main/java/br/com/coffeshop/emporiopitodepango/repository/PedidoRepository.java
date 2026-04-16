
package br.com.coffeshop.emporiopitodepango.repository;



import br.com.coffeshop.emporiopitodepango.model.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {

    public void salvar(Pedido pedido) {
        String sql = "INSERT INTO pedido (numero_pedido, id_cliente, data_pedido, produto, valor_unitario, quantidade, total) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getNumeroPedido());
            stmt.setInt(2, pedido.getIdCliente());
            stmt.setString(3, pedido.getDataPedido());
            stmt.setString(4, pedido.getProduto());
            stmt.setDouble(5, pedido.getValorUnitario());
            stmt.setInt(6, pedido.getQuantidade());
            stmt.setDouble(7, pedido.getTotal());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar pedido: " + e.getMessage(), e);
        }
    }

    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();

        String sql = """
            SELECT p.numero_pedido, p.id_cliente, c.nome AS nome_cliente, p.data_pedido,
                   p.produto, p.valor_unitario, p.quantidade, p.total
            FROM pedido p
            LEFT JOIN cliente c ON p.id_cliente = c.id_cliente
        """;

        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pedido pedido = new Pedido(
                    rs.getInt("numero_pedido"),
                    rs.getInt("id_cliente"),
                    rs.getString("nome_cliente"),
                    rs.getString("data_pedido"),
                    rs.getString("produto"),
                    rs.getDouble("valor_unitario"),
                    rs.getInt("quantidade")
                );
                pedido.setTotal(rs.getDouble("total"));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pedidos: " + e.getMessage(), e);
        }

        return pedidos;
    }

    public Pedido buscarPorNumero(int numeroPedido) {
        String sql = """
            SELECT p.numero_pedido, p.id_cliente, c.nome AS nome_cliente, p.data_pedido,
                   p.produto, p.valor_unitario, p.quantidade, p.total
            FROM pedido p
            LEFT JOIN cliente c ON p.id_cliente = c.id_cliente
            WHERE p.numero_pedido = ?
        """;

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numeroPedido);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pedido pedido = new Pedido(
                    rs.getInt("numero_pedido"),
                    rs.getInt("id_cliente"),
                    rs.getString("nome_cliente"),
                    rs.getString("data_pedido"),
                    rs.getString("produto"),
                    rs.getDouble("valor_unitario"),
                    rs.getInt("quantidade")
                );
                pedido.setTotal(rs.getDouble("total"));
                return pedido;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedido: " + e.getMessage(), e);
        }

        return null;
    }

    public void atualizar(Pedido pedido) {
        String sql = """
            UPDATE pedido
            SET id_cliente = ?, data_pedido = ?, produto = ?, valor_unitario = ?, quantidade = ?, total = ?
            WHERE numero_pedido = ?
        """;

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setString(2, pedido.getDataPedido());
            stmt.setString(3, pedido.getProduto());
            stmt.setDouble(4, pedido.getValorUnitario());
            stmt.setInt(5, pedido.getQuantidade());
            stmt.setDouble(6, pedido.getTotal());
            stmt.setInt(7, pedido.getNumeroPedido());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pedido: " + e.getMessage(), e);
        }
    }

    public void excluir(int numeroPedido) {
        String sql = "DELETE FROM pedido WHERE numero_pedido = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numeroPedido);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir pedido: " + e.getMessage(), e);
        }
    }
}