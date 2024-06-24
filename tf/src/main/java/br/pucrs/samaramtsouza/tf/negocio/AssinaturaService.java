package br.pucrs.samaramtsouza.tf.negocio;

import br.pucrs.samaramtsouza.tf.persistencia.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AssinaturaService {
    @Autowired
    private IAssinaturaRepository assinaturaRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IAplicativoRepository aplicativoRepository;

    public List<Assinatura> listarAssinaturasPorCliente(Long codcli) {
        Cliente cliente = clienteRepository.findById(codcli).orElseThrow();
        return assinaturaRepository.findByCliente(cliente);
    }

    public List<Assinatura> listarAssinaturasPorAplicativo(Long codapp) {
        Aplicativo aplicativo = aplicativoRepository.findById(codapp).orElseThrow();
        return assinaturaRepository.findByAplicativo(aplicativo);
    }

    public Assinatura getAssinaturaById(Long id) {
        return assinaturaRepository.findById(id).orElse(null);
    }
}