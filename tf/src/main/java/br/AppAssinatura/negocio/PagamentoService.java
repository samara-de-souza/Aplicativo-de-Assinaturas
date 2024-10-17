package br.AppAssinatura.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.AppAssinatura.persistencia.Assinatura;
import br.AppAssinatura.persistencia.IAssinaturaRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PagamentoService {

    @Autowired
    private IAssinaturaRepository assinaturaRepository;

    public Map<String, String> registrarPagamento(Long codAssinatura, int dia, int mes, int ano, Double valorPago) {
        Assinatura assinatura = assinaturaRepository.findById(codAssinatura)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, dia);
        cal.set(Calendar.MONTH, mes - 1); // Meses em Java começam do 0
        cal.set(Calendar.YEAR, ano);
        Date dataPagamento = cal.getTime();

        Map<String, String> response = new HashMap<>();
        if (valorPago.equals(assinatura.getAplicativo().getCustoMensal())) {
            assinatura.setFimVigencia(new Date(assinatura.getFimVigencia().getTime() + 30L * 24 * 60 * 60 * 1000));
            assinaturaRepository.save(assinatura);
            response.put("status", "PAGAMENTO_OK");
        } else {
            response.put("status", "VALOR_INCORRETO");
        }
        response.put("data", dataPagamento.toString());
        response.put("valor", valorPago.toString());

        return response;
    }
}
