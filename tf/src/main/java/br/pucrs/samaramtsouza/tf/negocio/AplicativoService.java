package br.pucrs.samaramtsouza.tf.negocio;

import br.pucrs.samaramtsouza.tf.persistencia.IAplicativoRepository;
import br.pucrs.samaramtsouza.tf.persistencia.Aplicativo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
