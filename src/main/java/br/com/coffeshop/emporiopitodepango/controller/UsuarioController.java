package br.com.coffeshop.emporiopitodepango.controller;

import br.com.coffeshop.emporiopitodepango.model.Usuario;
import br.com.coffeshop.emporiopitodepango.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "admin/usuarios";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/usuario-form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Usuario usuario,
                          @RequestParam String senha,
                          Model model) {
        try {
            usuarioService.cadastrar(usuario, senha);
            return "redirect:/admin/usuarios";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("usuario", usuario);
            return "admin/usuario-form";
        }
    }

    @GetMapping("/excluir/{nome}")
    public String excluir(@PathVariable String nome) {
        usuarioService.excluir(nome);
        return "redirect:/admin/usuarios";
    }
}
