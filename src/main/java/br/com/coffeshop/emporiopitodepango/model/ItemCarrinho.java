package br.com.coffeshop.emporiopitodepango.model;

public class ItemCarrinho {

    private int codigoProduto;
    private String nome;
    private double valorUnitario;
    private int quantidade;
    private String imagemUrl;


    public ItemCarrinho(int codigoProduto, String nome, double valorUnitario, int quantidade, String imagemUrl) {
        this.codigoProduto = codigoProduto;
        this.nome = nome;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.imagemUrl = imagemUrl;
    }
     
    public ItemCarrinho(int codigoProduto, String nome, double valorUnitario, int quantidade) {
        this.codigoProduto = codigoProduto;
        this.nome = nome;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade; }

    public double getSubtotal() {
        return valorUnitario * quantidade;
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(int codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
    
    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}
