// O contador do carrinho no cabeçalho agora vem do servidor (carrinho de
// sessão real), não é mais controlado por localStorage.
function adicionarCarrinho() {
    alert("Esta página de produto ainda é uma vitrine estática - use o catálogo em /produtos para comprar de verdade.");
}

// Elementos de busca e filtro
const buscaProduto = document.getElementById("buscaProduto");
const btnBuscar = document.getElementById("btnBuscar");
const filtroCategoria = document.getElementById("filtroCategoria");
const listaProdutos = document.getElementById("listaProdutos");

// 1. Lógica de busca dinâmica por texto (filtra os cards renderizados pelo Thymeleaf na tela)
function executarBusca() {
    if (!buscaProduto) return;
    
    const termo = buscaProduto.value.toLowerCase().trim();
    const cards = document.querySelectorAll(".card-produto");

    cards.forEach(card => {
        const nomeElemento = card.querySelector("h3");
        if (!nomeElemento) return;

        const nomeProduto = nomeElemento.textContent.toLowerCase();
        
        // Mostra ou esconde o card em tempo real conforme o texto digitado
        if (nomeProduto.includes(termo)) {
            card.style.display = "block";
        } else {
            card.style.display = "none";
        }
    });
}

// Eventos da barra de pesquisa e botão de lupa
if (buscaProduto) {
    buscaProduto.addEventListener("input", executarBusca);
}

if (btnBuscar) {
    btnBuscar.addEventListener("click", executarBusca);
}

// 2. Funções de controle de quantidade (mantidas para as páginas que as utilizam)
let quantidadeProduto = 1;
let quantidadeCarrinho = 1;
const precoBaseCarrinho = 39.90;

function alterarQuantidade(valor) {
    const span = document.getElementById("quantidadeProduto");
    if (!span) return;

    quantidadeProduto += valor;

    if (quantidadeProduto < 1) {
        quantidadeProduto = 1;
    }

    span.textContent = quantidadeProduto;
}

function alterarQuantidadeCarrinho(valor) {
    const span = document.getElementById("quantidadeCarrinho");
    const subtotal = document.getElementById("subtotalCarrinho");
    const total = document.getElementById("totalCarrinho");

    if (!span || !subtotal || !total) return;

    quantidadeCarrinho += valor;

    if (quantidadeCarrinho < 1) {
        quantidadeCarrinho = 1;
    }

    const valorTotal = precoBaseCarrinho * quantidadeCarrinho;
    const valorFormatado = valorTotal.toLocaleString("pt-BR", {
        style: "currency",
        currency: "BRL"
    });

    span.textContent = quantidadeCarrinho;
    subtotal.textContent = valorFormatado;
    total.textContent = valorFormatado;
}

function limparCarrinho() {
    localStorage.setItem("contadorCarrinho", 0);
    alert("Item removido do carrinho.");
    location.reload();
}