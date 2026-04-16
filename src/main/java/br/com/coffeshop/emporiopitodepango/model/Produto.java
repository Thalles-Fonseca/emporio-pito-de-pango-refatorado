
package br.com.coffeshop.emporiopitodepango.model;


public class Produto {

    private int codigo;
    private String nome;
    private String fornecedor;
    private int quantidade;
    private String dataCadastro;
    private String descricao;
    private double valor;
    private String categoria;

    public Produto() {
    }

    public Produto(int codigo, String nome, String fornecedor, int quantidade,
                   String dataCadastro, String descricao, double valor, String categoria) {
        this.codigo = codigo;
        this.nome = nome;
        this.fornecedor = fornecedor;
        this.quantidade = quantidade;
        this.dataCadastro = dataCadastro;
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    } 

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    } 

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    } 

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    } 

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    } 

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    } 

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}