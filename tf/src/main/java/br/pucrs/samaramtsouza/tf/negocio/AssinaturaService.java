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

    @Autowired
    private VendaAssinatura vendaAssinatura;

    public Assinatura criarAssinatura(Long codCliente, Long codAplicativo) {
        Cliente cliente = clienteRepository.findById(codCliente).orElseThrow();
        Aplicativo aplicativo = aplicativoRepository.findById(codAplicativo).orElseThrow();
        Assinatura assinatura = new Assinatura();
        assinatura.setCliente(cliente);
        assinatura.setAplicativo(aplicativo);
        assinatura.setInicioVigencia(new Date());
        assinatura.setFimVigencia(vendaAssinatura.calcularNovaDataValidade(assinatura.getInicioVigencia(), 7)); // 7 dias grátis
        return assinaturaRepository.save(assinatura);
    }

    public List<Assinatura> listarAssinaturas(String tipo) {
        // Implementar lógica de filtro de assinaturas baseado no tipo
        return assinaturaRepository.findAll(); // Placeholder
    }

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