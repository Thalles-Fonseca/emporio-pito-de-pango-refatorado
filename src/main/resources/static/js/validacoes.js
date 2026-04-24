const formContato = document.getElementById("formContato");
const mensagemErro = document.getElementById("mensagemErro");

formContato.addEventListener("submit", function(event) {
    event.preventDefault();

    const nome = document.getElementById("nome").value.trim();
    const telefone = document.getElementById("telefone").value.trim();
    const email = document.getElementById("email").value.trim();
    const categoria = document.getElementById("categoria").value;
    const mensagem = document.getElementById("mensagem").value.trim();

    if (nome.length < 3) {
        exibirMensagem("Informe um nome válido.", "erro");
        return;
    }

    if (telefone.length < 10) {
        exibirMensagem("Informe um telefone válido.", "erro");
        return;
    }

    if (!email.includes("@") || !email.includes(".")) {
        exibirMensagem("Informe um e-mail válido.", "erro");
        return;
    }

    if (categoria === "") {
        exibirMensagem("Selecione uma categoria de interesse.", "erro");
        return;
    }

    if (mensagem.length < 10) {
        exibirMensagem("Digite uma mensagem com pelo menos 10 caracteres.", "erro");
        return;
    }

    exibirMensagem("Mensagem enviada com sucesso! Em breve entraremos em contato.", "sucesso");
    formContato.reset();
});

function exibirMensagem(texto, tipo) {
    mensagemErro.textContent = texto;

    if (tipo === "erro") {
        mensagemErro.style.color = "#9b2f24";
    } else {
        mensagemErro.style.color = "#5c7c43";
    }
}
