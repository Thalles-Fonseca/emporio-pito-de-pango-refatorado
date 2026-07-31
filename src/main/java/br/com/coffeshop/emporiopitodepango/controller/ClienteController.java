package br.com.coffeshop.emporiopitodepango.controller;

import br.com.coffeshop.emporiopitodepango.model.Cliente;
import br.com.coffeshop.emporiopitodepango.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String termo, Model model) {
        boolean pesquisando = termo != null && !termo.trim().isEmpty();
        model.addAttribute("clientes", pesquisando ? clienteService.buscar(termo) : clienteService.listarTodos());
        model.addAttribute("termo", termo);
        return "admin/clientes";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("modoEdicao", false);
        return "admin/cliente-form";
    }

    @GetMapping("/editar/{cpf}")
    public String editar(@PathVariable String cpf, Model model) {
        Cliente cliente = clienteService.buscarPorCpf(cpf);
        if (cliente == null) {
            return "redirect:/admin/clientes";
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("modoEdicao", true);
        return "admin/cliente-form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Cliente cliente,
                          @RequestParam(defaultValue = "false") boolean modoEdicao,
                          Model model) {
        try {
            if (modoEdicao) {
                clienteService.atualizar(cliente);
            } else {
                clienteService.salvar(cliente);
            }
            return "redirect:/admin/clientes";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("cliente", cliente);
            model.addAttribute("modoEdicao", modoEdicao);
            return "admin/cliente-form";
        }
    }

    @GetMapping("/excluir/{cpf}")
    public String excluir(@PathVariable String cpf) {
        clienteService.excluir(cpf);
        return "redirect:/admin/clientes";
    }
}
