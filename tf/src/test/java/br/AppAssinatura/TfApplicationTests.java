package br.AppAssinatura;

import org.junit.jupiter.api.Test;

import br.AppAssinatura.persistencia.Aplicativo;
import br.AppAssinatura.persistencia.Assinatura;
import br.AppAssinatura.persistencia.Cliente;
import br.AppAssinatura.persistencia.Pagamento;
import br.AppAssinatura.persistencia.Usuario;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class TfApplicationTests {

    // Classe Cliente
    @Test
    public void testGetSetCodigo() {
        Cliente cliente = new Cliente();
        Long codigo = 1L;
        cliente.setCodigo(codigo);
        assertEquals(codigo, cliente.getCodigo());
    }

    @Test
    public void testGetSetNome() {
        Cliente cliente = new Cliente();
        String nome = "Cliente Teste";
        cliente.setNome(nome);
        assertEquals(nome, cliente.getNome());
    }

    @Test
    public void testGetSetEmail() {
        Cliente cliente = new Cliente();
        String email = "cliente@teste.com";
        cliente.setEmail(email);
        assertEquals(email, cliente.getEmail());
    }

    // Classe Pagamento
    @Test
    public void testGetSetCodigoPagamento() {
        Pagamento pagamento = new Pagamento();
        Long codigo = 1L;
        pagamento.setCodigo(codigo);
        assertEquals(codigo, pagamento.getCodigo());
    }

    @Test
    public void testGetSetAssinatura() {
        Pagamento pagamento = new Pagamento();
        Assinatura assinatura = new Assinatura();
        pagamento.setAssinatura(assinatura);
        assertEquals(assinatura, pagamento.getAssinatura());
    }

    @Test
    public void testGetSetValorPago() {
        Pagamento pagamento = new Pagamento();
        double valorPago = 99.99;
        pagamento.setValorPago(valorPago);
        assertEquals(valorPago, pagamento.getValorPago(), 0.001);
    }

    @Test
    public void testGetSetDataPagamento() {
        Pagamento pagamento = new Pagamento();
        Date dataPagamento = new Date();
        pagamento.setDataPagamento(dataPagamento);
        assertEquals(dataPagamento, pagamento.getDataPagamento());
    }

    @Test
    public void testGetSetPromocao() {
        Pagamento pagamento = new Pagamento();
        String promocao = "Desconto10";
        pagamento.setPromocao(promocao);
        assertEquals(promocao, pagamento.getPromocao());
    }

    // Classe Assinatura
    @Test
    public void testGetSetCodigoAssinatura() {
        Assinatura assinatura = new Assinatura();
        Long codigo = 1L;
        assinatura.setCodigo(codigo);
        assertEquals(codigo, assinatura.getCodigo());
    }

    @Test
    public void testGetSetAplicativo() {
        Assinatura assinatura = new Assinatura();
        Aplicativo aplicativo = new Aplicativo();
        assinatura.setAplicativo(aplicativo);
        assertEquals(aplicativo, assinatura.getAplicativo());
    }

    @Test
    public void testGetSetCliente() {
        Assinatura assinatura = new Assinatura();
        Cliente cliente = new Cliente();
        assinatura.setCliente(cliente);
        assertEquals(cliente, assinatura.getCliente());
    }

    @Test
    public void testGetSetInicioVigencia() {
        Assinatura assinatura = new Assinatura();
        Date inicioVigencia = new Date();
        assinatura.setInicioVigencia(inicioVigencia);
        assertEquals(inicioVigencia, assinatura.getInicioVigencia());
    }

    @Test
    public void testGetSetFimVigencia() {
        Assinatura assinatura = new Assinatura();
        Date fimVigencia = new Date();
        assinatura.setFimVigencia(fimVigencia);
        assertEquals(fimVigencia, assinatura.getFimVigencia());
    }

    // Classe Aplicativo

    @Test
    public void testGetSetCodigoAplicativo() {
        Aplicativo aplicativo = new Aplicativo();
        Long codigo = 1L;
        aplicativo.setCodigo(codigo);
        assertEquals(codigo, aplicativo.getCodigo());
    }

    @Test
    public void testGetSetNomeAplicativo() {
        Aplicativo aplicativo = new Aplicativo();
        String nome = "Nome do Aplicativo";
        aplicativo.setNome(nome);
        assertEquals(nome, aplicativo.getNome());
    }

    @Test
    public void testGetSetCustoMensal() {
        Aplicativo aplicativo = new Aplicativo();
        double custoMensal = 9.99;
        aplicativo.setCustoMensal(custoMensal);
        assertEquals(custoMensal, aplicativo.getCustoMensal());
    }

    // Classe Usuario
    @Test
    public void testGetSetUsuario() {
        Usuario usuario = new Usuario();
        String nomeUsuario = "NomeUsuario";
        usuario.setUsuario(nomeUsuario);
        assertEquals(nomeUsuario, usuario.getUsuario());
    }

    @Test
    public void testGetSetSenha() {
        Usuario usuario = new Usuario();
        String senha = "SenhaSegura";
        usuario.setSenha(senha);
        assertEquals(senha, usuario.getSenha());
    }
 
}
