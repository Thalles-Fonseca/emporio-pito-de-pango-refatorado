package br.com.coffeshop.emporiopitodepango.controller;

import br.com.coffeshop.emporiopitodepango.model.Carrinho;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Disponibiliza "totalItensCarrinho" pra todas as páginas, sem cada
 * controller precisar adicionar isso manualmente ao Model. É seguro injetar
 * o Carrinho (bean de sessão) aqui porque @SessionScope usa um proxy que
 * resolve pra sessão certa a cada requisição.
 */
@ControllerAdvice
public class CarrinhoModelAdvice {

    private final Carrinho carrinho;

    public CarrinhoModelAdvice(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    @ModelAttribute("totalItensCarrinho")
    public int totalItensCarrinho() {
        return carrinho.getQuantidadeTotalItens();
    }
}
