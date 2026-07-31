package br.com.coffeshop.emporiopitodepango.model;

public class Usuario {

    public enum Perfil {
        GERENTE,
        FINANCEIRO,
        ATENDENTE
    }

    private String nome;
    private String senha; // sempre armazenada como hash (BCrypt), nunca texto puro
    private Perfil perfil;

    public Usuario() {
    }

    public Usuario(String nome, String senha, Perfil perfil) {
        this.nome = nome;
        this.senha = senha;
        this.perfil = perfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
