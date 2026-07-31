package br.com.coffeshop.emporiopitodepango.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String erro,
                         @RequestParam(required = false) String logout,
                         Model model) {
        if (erro != null) {
            model.addAttribute("erro", "Usuário ou senha inválidos.");
        }
        if (logout != null) {
            model.addAttribute("mensagem", "Você saiu do painel administrativo.");
        }
        return "admin/login";
    }

    @GetMapping
    public String dashboard() {
        return "admin/dashboard";
    }
}
