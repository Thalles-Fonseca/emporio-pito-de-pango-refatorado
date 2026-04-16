
package br.com.coffeshop.emporiopitodepango.model;


public class Pedido {

    private int numeroPedido;
    private int idCliente;
    private String nomeCliente;
    private String dataPedido;
    private String produto;
    private double valorUnitario;
    private int quantidade;
    private double total;

    public Pedido() {
    }

    public Pedido(int numeroPedido, int idCliente, String nomeCliente, String dataPedido,
                  String produto, double valorUnitario, int quantidade) {
        this.numeroPedido = numeroPedido;
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.dataPedido = dataPedido;
        this.produto = produto;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.total = valorUnitario * quantidade;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}