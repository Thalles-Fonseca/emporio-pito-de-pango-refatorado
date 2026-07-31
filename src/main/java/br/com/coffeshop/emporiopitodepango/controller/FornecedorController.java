package br.com.coffeshop.emporiopitodepango.controller;

import br.com.coffeshop.emporiopitodepango.model.Fornecedor;
import br.com.coffeshop.emporiopitodepango.service.FornecedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("fornecedores", fornecedorService.listarTodos());
        return "admin/fornecedores";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        model.addAttribute("modoEdicao", false);
        return "admin/fornecedor-form";
    }

    @GetMapping("/editar/{cnpj}")
    public String editar(@PathVariable String cnpj, Model model) {
        Fornecedor fornecedor = fornecedorService.buscarPorCnpj(cnpj);
        if (fornecedor == null) {
            return "redirect:/admin/fornecedores";
        }
        model.addAttribute("fornecedor", fornecedor);
        model.addAttribute("modoEdicao", true);
        return "admin/fornecedor-form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Fornecedor fornecedor,
                          @RequestParam(defaultValue = "false") boolean modoEdicao,
                          Model model) {
        try {
            if (modoEdicao) {
                fornecedorService.atualizar(fornecedor);
            } else {
                fornecedorService.salvar(fornecedor);
            }
            return "redirect:/admin/fornecedores";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("fornecedor", fornecedor);
            model.addAttribute("modoEdicao", modoEdicao);
            return "admin/fornecedor-form";
        }
    }

    @GetMapping("/excluir/{cnpj}")
    public String excluir(@PathVariable String cnpj) {
        fornecedorService.excluir(cnpj);
        return "redirect:/admin/fornecedores";
    }
}
