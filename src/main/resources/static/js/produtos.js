let contador = Number(localStorage.getItem("contadorCarrinho")) || 0;

const contadorElemento = document.getElementById("contadorCarrinho");

if (contadorElemento) {
    contadorElemento.textContent = contador;
}

function adicionarCarrinho() {
    contador++;
    localStorage.setItem("contadorCarrinho", contador);

    if (contadorElemento) {
        contadorElemento.textContent = contador;
    }

    alert("Produto adicionado ao carrinho!");
}

const produtos = [
    { nome: "Café Especial", categoria: "cafe-sem", texto: "Grãos sem terpenos", preco: "R$ 39,90" },
    { nome: "Café com Terpenos", categoria: "cafe-com", texto: "Grãos com terpenos", preco: "R$ 49,90" },
    { nome: "Salgado Artesanal", categoria: "salgados", texto: "Salgados", preco: "R$ 12,00" },
    { nome: "Chocolate em Barra", categoria: "chocolate", texto: "Chocolate em barra", preco: "R$ 19,90" },
    { nome: "Filtro de Café", categoria: "filtros", texto: "Filtros e passadores", preco: "R$ 15,00" },
    { nome: "Maçarico", categoria: "isqueiros", texto: "Isqueiros/Maçaricos", preco: "R$ 35,00" },
    { nome: "Sedas Premium", categoria: "sedas", texto: "Sedas", preco: "R$ 8,00" },
    { nome: "Blunts", categoria: "blunts", texto: "Blunts", preco: "R$ 10,00" },
    { nome: "Piteiras", categoria: "piteiras", texto: "Piteiras", preco: "R$ 7,00" },
    { nome: "Dichavador", categoria: "dichavador", texto: "Dichavador", preco: "R$ 29,90" }
];

const listaProdutos = document.getElementById("listaProdutos");
const buscaProduto = document.getElementById("buscaProduto");
const filtroCategoria = document.getElementById("filtroCategoria");

function renderizarProdutos(lista) {
    if (!listaProdutos) return;

    listaProdutos.innerHTML = "";

    lista.forEach(produto => {
        listaProdutos.innerHTML += `
            <div class="card-produto">
                <div class="img-placeholder">Imagem</div>
                <h3>${produto.nome}</h3>
                <p>${produto.texto}</p>
                <strong>${produto.preco}</strong>
                <button onclick="adicionarCarrinho()">Adicionar</button>
            </div>
        `;
    });
}

function filtrarProdutos() {
    const termo = buscaProduto.value.toLowerCase();
    const categoria = filtroCategoria.value;

    const filtrados = produtos.filter(produto => {
        const correspondeBusca = produto.nome.toLowerCase().includes(termo);
        const correspondeCategoria = categoria === "todos" || produto.categoria === categoria;

        return correspondeBusca && correspondeCategoria;
    });

    renderizarProdutos(filtrados);
}

if (listaProdutos) {
    renderizarProdutos(produtos);

    buscaProduto.addEventListener("input", filtrarProdutos);
    filtroCategoria.addEventListener("change", filtrarProdutos);
}
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