package br.pucrs.samaramtsouza.tf.negocio;

import br.pucrs.samaramtsouza.tf.persistencia.*;
import jakarta.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssinaturaService {

    @Autowired
    private IAssinaturaRepository assinaturaRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IAplicativoRepository aplicativoRepository;

    public Assinatura criarAssinatura(Long codCliente, Long codAplicativo) {
        Cliente cliente = clienteRepository.findById(codCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + codCliente));
        Aplicativo aplicativo = aplicativoRepository.findById(codAplicativo)
                .orElseThrow(() -> new RuntimeException("Aplicativo não encontrado: " + codAplicativo));
        
        Date fimVigencia = new Date(new Date().getTime() + 30L * 24 * 60 * 60 * 1000); // 30 dias
        
        Assinatura assinatura = new Assinatura(1L, aplicativo, cliente, new Date(), fimVigencia);
        
        return assinaturaRepository.save(assinatura);
    }

    public List<Assinatura> listarAssinaturas(String tipo) {
        List<Assinatura> todasAssinaturas = assinaturaRepository.findAll();
        switch (tipo.toUpperCase()) {
            case "ATIVAS":
                return todasAssinaturas.stream()
                        .filter(a -> a.getFimVigencia().after(new Date()))
                        .collect(Collectors.toList());
            case "CANCELADAS":
                return todasAssinaturas.stream()
                        .filter(a -> a.getFimVigencia().before(new Date()))
                        .collect(Collectors.toList());
            default:
                return todasAssinaturas;
        }
    }

    public List<Assinatura> listarAssinaturasPorCliente(Long codCliente) {
        Cliente cliente = clienteRepository.findById(codCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + codCliente));
        return assinaturaRepository.findByCliente(cliente);
    }

    public List<Assinatura> listarAssinaturasPorAplicativo(Long codAplicativo) {
        Aplicativo aplicativo = aplicativoRepository.findById(codAplicativo)
                .orElseThrow(() -> new RuntimeException("Aplicativo não encontrado: " + codAplicativo));
        return assinaturaRepository.findByAplicativo(aplicativo);
    }

    public Assinatura getAssinaturaById(Long codAssinatura) {
        return assinaturaRepository.findById(codAssinatura).orElse(null);
    }
}
