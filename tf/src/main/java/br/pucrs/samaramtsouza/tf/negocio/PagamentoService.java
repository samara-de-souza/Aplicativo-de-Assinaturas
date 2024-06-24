package br.pucrs.samaramtsouza.tf.negocio;

import br.pucrs.samaramtsouza.tf.persistencia.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PagamentoService {
    @Autowired
    private IPagamentoRepository pagamentoRepository;

    @Autowired
    private IAssinaturaRepository assinaturaRepository;

    @Autowired
    private VendaAssinatura vendaAssinatura;

}