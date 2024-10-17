package br.AppAssinatura.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.AppAssinatura.persistencia.*;

import java.util.Date;
import java.util.Calendar;

@Component
public class VendaAssinatura {

    @Autowired
    public VendaAssinatura() {
    }

    public double calculaPreco(Assinatura assinatura, Pagamento pagamento) {
        
        if ("ANUAL".equalsIgnoreCase(pagamento.getPromocao())) {
            return assinatura.getAplicativo().getCustoMensal() * 12 * 0.6; // 40% de desconto para pagamento anual
        } else if ("PROMO30_45".equalsIgnoreCase(pagamento.getPromocao())) {
            return assinatura.getAplicativo().getCustoMensal() * 30.0 / 45.0; // Pague 30 dias e ganhe 45 dias
        } else {
            return assinatura.getAplicativo().getCustoMensal();
        }
    }

    public Date calcularNovaDataValidade(Date dataAtual, int diasAdicionais) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataAtual);
        calendar.add(Calendar.DAY_OF_MONTH, diasAdicionais);
        return calendar.getTime();
    }
}
