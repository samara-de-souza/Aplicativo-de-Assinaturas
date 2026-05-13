package br.AppAssinatura.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.AppAssinatura.persistencia.IUsuarioRepository;
import br.AppAssinatura.persistencia.Usuario;

@Service
public class UsuarioService {
    @Autowired
    private IUsuarioRepository usuarioRepository;

    public boolean isUsuarioValido(String usuario, String senha) {
        Usuario foundUsuario = usuarioRepository.findById(usuario).orElse(null);
        return foundUsuario != null && foundUsuario.getSenha().equals(senha);
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
