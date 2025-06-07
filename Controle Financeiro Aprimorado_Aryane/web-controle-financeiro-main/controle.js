const htmlForm = document.querySelector("#form");
const descTransacaoInput = document.getElementById("descricao");
const valorTransacaoInput = document.getElementById("montante");
const balancoH1 = document.querySelector("#balanco");
const receitaP = document.querySelector("#din-positivo");
const despesaP = document.querySelector("#din-negativo");
const transacoesUl = document.querySelector("#transacoes");

// Local Storage
const chave_transacoes_storage = "if_financas";
const chave_id_storage = "if_financas_proximo_id";

let transacoesSalvas = JSON.parse(localStorage.getItem(chave_transacoes_storage)) || [];
let proximoIdTransacao = parseInt(localStorage.getItem(chave_id_storage)) || 0;

let tipoSelecionado = null;

transacoesSalvas.forEach(transacao => {
    addTransacaoALista(transacao);
});

atualizarBalanco();

htmlForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const descricao = descTransacaoInput.value.trim();
    const valorStr = valorTransacaoInput.value.trim();

    if (!descricao || !valorStr) {
        alert("Preencha a descrição e valor da transação.");
        return;
    }

    // Modal dos botões da Receita/Despesa

    abrirModalTipo(parseFloat(valorStr), descricao);
});

function abrirModalTipo(valor, descricao) {
    const modal = document.createElement("div");
    modal.classList.add("tipo-modal");
    modal.innerHTML = `
    <div class="tipo-content">
      <p>Essa transação é uma:</p>
      <button id="btn-receita" class="btn-receita">Receita</button>
      <button id="btn-despesa" class="btn-despesa">Despesa</button>
    </div>
  `;

    document.body.appendChild(modal);

    document.getElementById("btn-receita").addEventListener("click", () => {
        criarTransacao(descricao, Math.abs(valor));
        fecharModalTipo(modal);
    });

    document.getElementById("btn-despesa").addEventListener("click", () => {
        criarTransacao(descricao, -Math.abs(valor));
        fecharModalTipo(modal);
    });
}

function fecharModalTipo(modal) {
    modal.remove();
    descTransacaoInput.value = "";
    valorTransacaoInput.value = "";
}

function criarTransacao(descricao, valor) {
    const transacao = {
        id: proximoIdTransacao++,
        descricao,
        valor
    };

    transacoesSalvas.push(transacao);
    localStorage.setItem(chave_transacoes_storage, JSON.stringify(transacoesSalvas));
    localStorage.setItem(chave_id_storage, proximoIdTransacao);

    addTransacaoALista(transacao);
    atualizarBalanco();
}

// + DOM

function addTransacaoALista(transacao) {
    const li = document.createElement("li");
    const classe = transacao.valor > 0 ? "positivo" : "negativo";
    const sinal = transacao.valor > 0 ? "" : "-";

    li.classList.add(classe);
    li.innerHTML = `
    <span class="transacao-id">${transacao.id}</span>
    <span class="transacao-desc">${transacao.descricao}</span> 
    <span>${sinal}R$${Math.abs(transacao.valor).toFixed(2)}</span>
    <button class="delete-btn" data-id="${transacao.id}">X</button>
  `;

    transacoesUl.appendChild(li);

    li.querySelector(".delete-btn").addEventListener("click", () => removerTransacao(transacao.id, li));
}

function atualizarBalanco() {
    let receitaTotal = 0;
    let despesaTotal = 0;

    transacoesSalvas.forEach(transacao => {
        if (transacao.valor > 0) {
            receitaTotal += transacao.valor;
        } else {
            despesaTotal += Math.abs(transacao.valor);
        }
    });

    const saldoAtual = receitaTotal - despesaTotal;

    balancoH1.innerHTML = `R$${saldoAtual.toFixed(2)}`;
    receitaP.innerHTML = `+ R$${receitaTotal.toFixed(2)}`;
    despesaP.innerHTML = `- R$${despesaTotal.toFixed(2)}`;

    atualizarStatus(saldoAtual);
}

// Card Status - Melhorar depois

function atualizarStatus(saldo) {
    const statusMsg = document.getElementById('status-msg');
    if (!statusMsg) return;

    if (saldo < 0) {
        statusMsg.textContent = "Cuidado, seu saldo está NEGATIVO.";
    } else if (saldo > 0) {
        statusMsg.textContent = "Continue assim, mantenha seu saldo POSITIVO.";
    } else {
        statusMsg.textContent = "Seu saldo está ZERADO.";
    }
}

function removerTransacao(id, li) {
    const index = transacoesSalvas.findIndex(t => t.id === id);
    if (index !== -1) {
        transacoesSalvas.splice(index, 1);
        localStorage.setItem(chave_transacoes_storage, JSON.stringify(transacoesSalvas));

        li.remove();

        atualizarBalanco();
    }
}
