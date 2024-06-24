package br.pucrs.samaramtsouza.tf.apresentacao;

import br.pucrs.samaramtsouza.tf.negocio.*;
import br.pucrs.samaramtsouza.tf.persistencia.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class AssinaturaController {

    @Autowired
    private AplicativoService aplicativoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AssinaturaService assinaturaService;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @PostMapping("/usuvalido")
    public boolean isUsuarioValido(@RequestBody Map<String, String> user) {
        String username = user.get("usuario");
        String password = user.get("senha");
        Usuario usuario = usuarioRepository.findById(username).orElse(null);
        return usuario != null && usuario.getSenha().equals(password);
    }

    @GetMapping("/clientes")
    public List<Cliente> getClientes() {
        return clienteService.listarClientes();
    }

    @GetMapping("/aplicativos")
    public List<Aplicativo> getAplicativos() {
        return aplicativoService.listarAplicativos();
    }

    @PostMapping("/assinaturas")
    public Assinatura criarAssinatura(@RequestBody Map<String, Long> data) {
        Long codCliente = data.get("codigoCliente");
        Long codAplicativo = data.get("codigoAplicativo");
        return assinaturaService.criarAssinatura(codCliente, codAplicativo);
    }


}
