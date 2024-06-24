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

    public Map<String, Object> registrarPagamento(Long codAssinatura, double valorPago, String promocao) {
        Assinatura assinatura = assinaturaRepository.findById(codAssinatura).orElseThrow();
        Pagamento pagamento = new Pagamento();
        pagamento.setAssinatura(assinatura);
        pagamento.setValorPago(valorPago);
        pagamento.setDataPagamento(new Date());
        pagamento.setPromocao(promocao);

        double valorEsperado = vendaAssinatura.calculaPreco(assinatura, pagamento);

        Map<String, Object> response = new HashMap<>();
        if (valorPago >= valorEsperado) {
            int diasAdicionais = 30;
            if ("PROMO30_45".equalsIgnoreCase(promocao)) {
                diasAdicionais = 45;
            } else if ("ANUAL".equalsIgnoreCase(promocao)) {
                diasAdicionais = 365;
            }
            assinatura.setFimVigencia(vendaAssinatura.calcularNovaDataValidade(assinatura.getFimVigencia(), diasAdicionais));
            assinaturaRepository.save(assinatura);
            response.put("status", "PAGAMENTO_OK");
            response.put("data", assinatura.getFimVigencia());
            response.put("valorEstornado", 0.0);
        } else {
            response.put("status", "VALOR_INCORRETO");
            response.put("data", assinatura.getFimVigencia());
            response.put("valorEstornado", valorPago);
        }

        pagamentoRepository.save(pagamento);
        return response;
    }
}