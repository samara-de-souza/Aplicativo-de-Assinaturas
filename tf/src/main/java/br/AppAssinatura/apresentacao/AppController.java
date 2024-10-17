package br.AppAssinatura.apresentacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.AppAssinatura.negocio.*;
import br.AppAssinatura.persistencia.*;

import java.util.*;

@RestController
public class AppController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AplicativoService aplicativoService;

    @Autowired
    private AssinaturaService assinaturaService;

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private IClienteRepository clienteRepository;

    // Endpoint para verificar se o usuário é válido
    @PostMapping("/usuvalido")
    public boolean isUsuarioValido(@RequestBody Map<String, String> user) {
        String username = user.get("usuario");
        String password = user.get("senha");
        return usuarioService.isUsuarioValido(username, password);
    }

    // Endpoint para registrar pagamento
    @PostMapping("/registrarpagamento")
    public Map<String, String> registrarPagamento(@RequestBody Map<String, Object> pagamentoData) {
        Long codAssinatura = ((Number) pagamentoData.get("codass")).longValue();
        int dia = (int) pagamentoData.get("dia");
        int mes = (int) pagamentoData.get("mes");
        int ano = (int) pagamentoData.get("ano");
        Double valorPago = (Double) pagamentoData.get("valorPago");
        return pagamentoService.registrarPagamento(codAssinatura, dia, mes, ano, valorPago);
    }

    // Endpoint para verificar se a assinatura é válida
    @GetMapping("/assinvalida/{codassin}")
    public boolean isAssinaturaValida(@PathVariable Long codassin) {
        Assinatura assinatura = assinaturaService.getAssinaturaById(codassin);
        return assinatura != null && assinatura.getFimVigencia().after(new Date());
    }

    // Endpoint para listar todos os clientes
    @GetMapping("/servcad/clientes")
    public List<Cliente> getClientes() {
        return clienteService.listarClientes();
    }

    // Endpoint para listar todos os aplicativos
    @GetMapping("/servcad/aplicativos")
    public List<Aplicativo> getAplicativos() {
        return aplicativoService.listarAplicativos();
    }

    // Endpoint para criar uma assinatura
    @PostMapping("/servcad/assinaturas")
    public Map<String, Object> criarAssinatura(@RequestBody Map<String, Long> data) {
        Long codCliente = data.get("codigoCliente");
        Long codAplicativo = data.get("codigoAplicativo");
        Assinatura assinatura = assinaturaService.criarAssinatura(codCliente, codAplicativo);
        
        Map<String, Object> response = new HashMap<>();
        response.put("codigoAssinatura", assinatura.getCodigo());
        response.put("codigoCliente", assinatura.getCliente().getCodigo());
        response.put("codigoAplicativo", assinatura.getAplicativo().getCodigo());
        response.put("dataInicio", assinatura.getInicioVigencia());
        response.put("dataFim", assinatura.getFimVigencia());
        return response;
    }

    // Endpoint para listar assinaturas por tipo
    @GetMapping("/servcad/assinaturas/{tipo}")
    public List<Assinatura> listarAssinaturas(@PathVariable String tipo) {
        return assinaturaService.listarAssinaturas(tipo);
    }

    // Endpoint para listar assinaturas por cliente
    @GetMapping("/servcad/assincli/{codcli}")
    public List<Assinatura> listarAssinaturasPorCliente(@PathVariable Long codcli) {
        return assinaturaService.listarAssinaturasPorCliente(codcli);
    }

    // Endpoint para listar assinaturas por aplicativo
    @GetMapping("/servcad/assinapp/{codapp}")
    public List<Assinatura> listarAssinaturasPorAplicativo(@PathVariable Long codapp) {
        return assinaturaService.listarAssinaturasPorAplicativo(codapp);
    }

    // Endpoint para atualizar o custo do aplicativo
    @PostMapping("/servcad/aplicativos/atualizacusto/{idAplicativo}")
    public Aplicativo atualizarCusto(@PathVariable Long idAplicativo, @RequestBody Map<String, Double> data) {
        Double custo = data.get("custo");
        return aplicativoService.atualizarCusto(idAplicativo, custo);
}

}
