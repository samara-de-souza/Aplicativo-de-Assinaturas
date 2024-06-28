document.addEventListener("DOMContentLoaded", function() {
    const btnClientes = document.getElementById('btnClientes');
    const btnAssinaturas = document.getElementById('btnAssinaturas');
    const btnAplicativos = document.getElementById('btnAplicativos');
    const btnPagamentos = document.getElementById('btnPagamentos');

    const clientesSection = document.getElementById('clientesSection');
    const assinaturasSection = document.getElementById('assinaturasSection');
    const aplicativosSection = document.getElementById('aplicativosSection');
    const pagamentosSection = document.getElementById('pagamentosSection');

    btnClientes.addEventListener('click', function() {
        showSection(clientesSection);
        fetchClientes();
    });

    btnAssinaturas.addEventListener('click', function() {
        showSection(assinaturasSection);
        fetchAssinaturas();
    });

    btnAplicativos.addEventListener('click', function() {
        showSection(aplicativosSection);
        fetchAplicativos();
    });

    btnPagamentos.addEventListener('click', function() {
        showSection(pagamentosSection);
        fetchPagamentos();
    });

    function showSection(section) {
        clientesSection.classList.remove('active');
        assinaturasSection.classList.remove('active');
        aplicativosSection.classList.remove('active');
        pagamentosSection.classList.remove('active');

        section.classList.add('active');
    }
});

function fetchClientes() {
    console.log('Fetching clientes...');
    fetch('/servcad/clientes')
        .then(response => {
            console.log('Response status:', response.status);
            if (!response.ok) {
                throw new Error('Erro ao buscar clientes');
            }
            return response.json();
        })
        .then(data => {
            console.log('Clientes data:', data);
            const listaClientes = document.getElementById('listaClientes');
            listaClientes.innerHTML = '';
            data.forEach(cliente => {
                const li = document.createElement('li');
                li.textContent = `Nome: ${cliente.nome}, Código: ${cliente.codigo}, Email: ${cliente.email}`;
                listaClientes.appendChild(li);
            });
        })
        .catch(error => {
            console.error('Erro:', error);
        });
}

function fetchAssinaturas() {
    console.log('Fetching assinaturas...');
    fetch('/servcad/assinaturas')
        .then(response => {
            console.log('Response status:', response.status);
            if (!response.ok) {
                throw new Error('Erro ao buscar assinaturas');
            }
            return response.json();
        })
        .then(data => {
            console.log('Assinaturas data:', data);
            const listaAssinaturas = document.getElementById('listaAssinaturas');
            listaAssinaturas.innerHTML = '';
            data.forEach(assinatura => {
                const li = document.createElement('li');
                li.textContent = `Cliente: ${assinatura.cliente.nome}, Aplicativo: ${assinatura.aplicativo.nome}`;
                listaAssinaturas.appendChild(li);
            });
        })
        .catch(error => {
            console.error('Erro:', error);
        });
}

function fetchAplicativos() {
    console.log('Fetching aplicativos...');
    fetch('/servcad/aplicativos')
        .then(response => {
            console.log('Response status:', response.status);
            if (!response.ok) {
                throw new Error('Erro ao buscar aplicativos');
            }
            return response.json();
        })
        .then(data => {
            console.log('Aplicativos data:', data);
            const listaAplicativos = document.getElementById('listaAplicativos');
            listaAplicativos.innerHTML = '';
            data.forEach(aplicativo => {
                const li = document.createElement('li');
                li.textContent = `Nome: ${aplicativo.nome}, Código: ${aplicativo.codigo}, Custo: ${aplicativo.custoMensal}`;
                listaAplicativos.appendChild(li);
            });
        })
        .catch(error => {
            console.error('Erro:', error);
        });
}

function fetchPagamentos() {
    console.log('Fetching pagamentos...');
    fetch('/servcad/pagamentos')
        .then(response => {
            console.log('Response status:', response.status);
            if (!response.ok) {
                throw new Error('Erro ao buscar pagamentos');
            }
            return response.json();
        })
        .then(data => {
            console.log('Pagamentos data:', data);
            const listaPagamentos = document.getElementById('listaPagamentos');
            listaPagamentos.innerHTML = '';
            data.forEach(pagamento => {
                const li = document.createElement('li');
                li.textContent = `Data: ${pagamento.dataPagamento}, Valor: ${pagamento.valorPago}`;
                listaPagamentos.appendChild(li);
            });
        })
        .catch(error => {
            console.error('Erro:', error);
        });
}

function cadastrarCliente() {
    const nome = document.getElementById('clienteNome').value;
    const codigo = document.getElementById('clienteCodigo').value;
    const email = document.getElementById('clienteEmail').value;

    console.log('Cadastrando cliente:', { nome, codigo, email });

    fetch('/servcad/clientes', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ nome, codigo, email }),
    })
    .then(response => {
        console.log('Response status:', response.status);
        if (!response.ok) {
            throw new Error('Erro ao cadastrar cliente');
        }
        return response.json();
    })
    .then(fetchClientes)
    .catch(error => {
        console.error('Erro:', error);
    });
}

function criarAssinatura() {
    const clienteCodigo = document.getElementById('assinaturaClienteCodigo').value;
    const aplicativoCodigo = document.getElementById('assinaturaAplicativoCodigo').value;

    console.log('Criando assinatura:', { clienteCodigo, aplicativoCodigo });

    fetch('/servcad/assinaturas', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ clienteCodigo, aplicativoCodigo }),
    })
    .then(response => {
        console.log('Response status:', response.status);
        if (!response.ok) {
            throw new Error('Erro ao criar assinatura');
        }
        return response.json();
    })
    .then(fetchAssinaturas)
    .catch(error => {
        console.error('Erro:', error);
    });
}

function atualizarCusto() {
    const codigo = document.getElementById('aplicativoCodigo').value;
    const custoMensal = document.getElementById('aplicativoCusto').value;

    console.log('Atualizando custo:', { codigo, custoMensal });

    fetch(`/servcad/aplicativos/atualizacusto/${codigo}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ custoMensal }),
    })
    .then(response => {
        console.log('Response status:', response.status);
        if (!response.ok) {
            throw new Error('Erro ao atualizar custo');
        }
        return response.json();
    })
    .then(fetchAplicativos)
    .catch(error => {
        console.error('Erro:', error);
    });
}

function solicitarRegistro() {
    const dataPagamento = document.getElementById('pagamentoData').value;
    const codigoPagamento = document.getElementById('pagamentoCodigo').value;
    const valorPagamento = document.getElementById('pagamentoValor').value;

    console.log('Solicitando registro:', { dataPagamento, codigoPagamento, valorPagamento });

    fetch('/registrarpagamento', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ dataPagamento, codigoPagamento, valorPagamento }),
    })
    .then(response => {
        console.log('Response status:', response.status);
        if (!response.ok) {
            throw new Error('Erro ao solicitar registro');
        }
        return response.json();
    })
    .then(fetchPagamentos)
    .catch(error => {
        console.error('Erro:', error);
    });
}
