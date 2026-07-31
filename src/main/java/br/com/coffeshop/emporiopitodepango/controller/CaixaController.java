package br.com.coffeshop.emporiopitodepango.controller;

import br.com.coffeshop.emporiopitodepango.model.Caixa;
import br.com.coffeshop.emporiopitodepango.service.CaixaService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/caixa")
public class CaixaController {

    private final CaixaService caixaService;

    public CaixaController(CaixaService caixaService) {
        this.caixaService = caixaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("registros", caixaService.listarTodos());
        return "admin/caixa";
    }

    @GetMapping("/novo")
    public String novo(Model model, Authentication authentication) {
        Caixa caixa = new Caixa();
        caixa.setOperador(authentication.getName()); // pre-preenche com quem esta logado
        model.addAttribute("caixa", caixa);
        return "admin/caixa-form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Caixa caixa, Model model) {
        try {
            caixaService.abrirRegistro(caixa);
            return "redirect:/admin/caixa";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("caixa", caixa);
            return "admin/caixa-form";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") int idCaixa) {
        caixaService.excluir(idCaixa);
        return "redirect:/admin/caixa";
    }
}
