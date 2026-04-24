package br.com.coffeshop.emporiopitodepango.controller;

import br.com.coffeshop.emporiopitodepango.model.Produto;
import br.com.coffeshop.emporiopitodepango.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService = new ProdutoService();

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        return "produtos";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        return "produto-form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto, Model model) {
        try {
            produtoService.salvar(produto);
            return "redirect:/produtos";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("produto", produto);
            return "produto-form";
        }
    }

    @GetMapping("/excluir/{codigo}")
    public String excluir(@PathVariable int codigo) {
        produtoService.excluir(codigo);
        return "redirect:/produtos";
    }
}