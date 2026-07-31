package br.com.coffeshop.emporiopitodepango.controller;

import br.com.coffeshop.emporiopitodepango.model.Pedido;
import br.com.coffeshop.emporiopitodepango.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Só consulta e exclusão de propósito - editar um pedido já feito é terreno
 * escorregadio (decisão tomada em conversa com o time). Quem precisar
 * corrigir algo faz um novo pedido ou pede pra excluir e refazer.
 */
@Controller
@RequestMapping("/admin/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodos());
        return "admin/pedidos";
    }

    @GetMapping("/{numero}")
    public String detalhe(@PathVariable int numero, Model model) {
        Pedido pedido = pedidoService.buscarPorNumero(numero);
        if (pedido == null) {
            return "redirect:/admin/pedidos";
        }
        model.addAttribute("pedido", pedido);
        return "admin/pedido-detalhe";
    }

    @GetMapping("/excluir/{numero}")
    public String excluir(@PathVariable int numero) {
        pedidoService.excluir(numero);
        return "redirect:/admin/pedidos";
    }
}
