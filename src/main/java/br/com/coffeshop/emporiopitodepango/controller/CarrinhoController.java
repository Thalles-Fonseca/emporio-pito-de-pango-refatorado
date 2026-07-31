package br.com.coffeshop.emporiopitodepango.controller;

import br.com.coffeshop.emporiopitodepango.model.Carrinho;
import br.com.coffeshop.emporiopitodepango.model.Cliente;
import br.com.coffeshop.emporiopitodepango.model.ItemCarrinho;
import br.com.coffeshop.emporiopitodepango.model.Pedido;
import br.com.coffeshop.emporiopitodepango.model.Produto;
import br.com.coffeshop.emporiopitodepango.service.ClienteService;
import br.com.coffeshop.emporiopitodepango.service.PedidoService;
import br.com.coffeshop.emporiopitodepango.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

/**
 * Checkout "convidado": não exige login de cliente (isso fica pra quando o
 * cadastro com benefícios for implementado). Só pede os dados necessários
 * pra registrar o pedido.
 */
@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    private final Carrinho carrinho;
    private final ProdutoService produtoService;
    private final ClienteService clienteService;
    private final PedidoService pedidoService;

    public CarrinhoController(Carrinho carrinho, ProdutoService produtoService,
                               ClienteService clienteService, PedidoService pedidoService) {
        this.carrinho = carrinho;
        this.produtoService = produtoService;
        this.clienteService = clienteService;
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public String verCarrinho(Model model) {
        model.addAttribute("itens", carrinho.getItens());
        model.addAttribute("total", carrinho.getTotal());
        return "carrinho";
    }

    @PostMapping("/adicionar")
    public String adicionar(@RequestParam int codigo,
                             @RequestParam(defaultValue = "1") int quantidade,
                             RedirectAttributes redirectAttributes) {
        try {
            Produto produto = produtoService.buscarPorCodigo(codigo);
            if (produto == null) {
                throw new IllegalArgumentException("Produto não encontrado.");
            }
            carrinho.adicionar(produto, quantidade);
            redirectAttributes.addFlashAttribute("mensagem", "Produto adicionado ao carrinho.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/produtos";
    }

    @PostMapping("/alterar/{codigo}")
    public String alterarQuantidade(@PathVariable int codigo, @RequestParam int quantidade) {
        carrinho.alterarQuantidade(codigo, quantidade);
        return "redirect:/carrinho";
    }

    @GetMapping("/remover/{codigo}")
    public String remover(@PathVariable int codigo) {
        carrinho.remover(codigo);
        return "redirect:/carrinho";
    }

    @GetMapping("/finalizar")
    public String formularioFinalizar(Model model) {
        if (carrinho.isVazio()) {
            return "redirect:/carrinho";
        }
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("total", carrinho.getTotal());
        return "checkout";
    }

    @PostMapping("/finalizar")
    public String finalizar(@ModelAttribute Cliente cliente, Model model) {
        if (carrinho.isVazio()) {
            return "redirect:/carrinho";
        }

        try {
            Cliente clienteFinal = clienteService.buscarPorCpf(cliente.getCpf());

            if (clienteFinal != null) {
                clienteFinal.setNome(cliente.getNome());
                clienteFinal.setTelefone(cliente.getTelefone());
                clienteFinal.setEmail(cliente.getEmail());
                clienteFinal.setEndereco(cliente.getEndereco());
                clienteService.atualizar(clienteFinal);
            } else {
                clienteService.salvar(cliente); // preenche cliente.getId() internamente
                clienteFinal = cliente;
            }

            String hoje = LocalDate.now().toString();
            int ultimoNumeroPedido = 0;

            // Cada item do carrinho vira uma linha em "pedido" (mesma
            // limitação que já existia no modelo do sistema desktop: não há
            // uma tabela de "itens do pedido" agrupando várias linhas sob um
            // único pedido). Todas as linhas compartilham cliente e data,
            // então dá pra reagrupá-las depois filtrando por esses dois campos.
            for (ItemCarrinho item : carrinho.getItens()) {
                Produto produto = produtoService.buscarPorCodigo(item.getCodigoProduto());
                if (produto == null || produto.getQuantidade() < item.getQuantidade()) {
                    throw new IllegalArgumentException(
                        "Estoque insuficiente para " + item.getNome() + ".");
                }

                Pedido pedido = new Pedido(0, clienteFinal.getId(), clienteFinal.getNome(),
                        hoje, item.getNome(), item.getValorUnitario(), item.getQuantidade());
                ultimoNumeroPedido = pedidoService.salvar(pedido);

                produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
                produtoService.atualizar(produto);
            }

            carrinho.limpar();
            model.addAttribute("numeroPedido", ultimoNumeroPedido);
            return "pedido-confirmado";

        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("cliente", cliente);
            model.addAttribute("total", carrinho.getTotal());
            return "checkout";
        }
    }
}
