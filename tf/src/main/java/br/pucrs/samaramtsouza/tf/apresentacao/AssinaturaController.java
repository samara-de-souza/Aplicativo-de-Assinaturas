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
    private IUsuarioRepository usuarioRepository;

    @PostMapping("/usuvalido")
    public boolean isUsuarioValido(@RequestBody Map<String, String> user) {
        String username = user.get("usuario");
        String password = user.get("senha");
        Usuario usuario = usuarioRepository.findById(username).orElse(null);
        return usuario != null && usuario.getSenha().equals(password);
    }


}
