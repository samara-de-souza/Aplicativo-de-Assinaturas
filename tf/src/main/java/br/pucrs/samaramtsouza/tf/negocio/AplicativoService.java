package br.pucrs.samaramtsouza.tf.negocio;

import br.pucrs.samaramtsouza.tf.persistencia.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AplicativoService {
    @Autowired
    private IAplicativoRepository aplicativoRepository;

    public List<Aplicativo> listarAplicativos() {
        return aplicativoRepository.findAll();
    }

    public Aplicativo salvarAplicativo(Aplicativo aplicativo) {
        return aplicativoRepository.save(aplicativo);
    }

    public Optional<Aplicativo> encontrarAplicativoPorId(Long id) {
        return aplicativoRepository.findById(id);
    }

    public void deletarAplicativo(Long id) {
        aplicativoRepository.deleteById(id);
    }
}
