package br.AppAssinatura.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.AppAssinatura.persistencia.Aplicativo;
import br.AppAssinatura.persistencia.IAplicativoRepository;

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

    public Aplicativo atualizarCusto(Long idAplicativo, Double custo) {
        Aplicativo aplicativo = aplicativoRepository.findById(idAplicativo)
                .orElseThrow(() -> new RuntimeException("Aplicativo não encontrado"));
        aplicativo.setCustoMensal(custo);
        return aplicativoRepository.save(aplicativo);
    }

}
