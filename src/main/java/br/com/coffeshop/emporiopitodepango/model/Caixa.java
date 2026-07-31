package br.com.coffeshop.emporiopitodepango.model;

public class Caixa {

    private int idCaixa;
    private String data;
    private String operador;
    private double saldoInicial;
    private double saldoAtual;
    private String descricao;

    public Caixa() {
    }

    public Caixa(int idCaixa, String data, String operador, double saldoInicial,
                 double saldoAtual, String descricao) {
        this.idCaixa = idCaixa;
        this.data = data;
        this.operador = operador;
        this.saldoInicial = saldoInicial;
        this.saldoAtual = saldoAtual;
        this.descricao = descricao;
    }

    public int getIdCaixa() {
        return idCaixa;
    }

    public void setIdCaixa(int idCaixa) {
        this.idCaixa = idCaixa;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public double getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
