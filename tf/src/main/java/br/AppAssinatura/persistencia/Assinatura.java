package br.AppAssinatura.persistencia;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Assinatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    @ManyToOne
    @JoinColumn(name = "cliente_codigo", nullable = false)
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "aplicativo_codigo", nullable = false)
    private Aplicativo aplicativo;
    private Date inicioVigencia;
    private Date fimVigencia;

    public Assinatura() {
    }

    public Assinatura(Long codigo, Aplicativo aplicativo, Cliente cliente, Date inicioVigencia, Date fimVigencia) {
        this.codigo = codigo;
        this.aplicativo = aplicativo;
        this.cliente = cliente;
        this.inicioVigencia = inicioVigencia;
        this.fimVigencia = fimVigencia;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Aplicativo getAplicativo() {
        return aplicativo;
    }

    public void setAplicativo(Aplicativo aplicativo) {
        this.aplicativo = aplicativo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(Date inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public Date getFimVigencia() {
        return fimVigencia;
    }

    public void setFimVigencia(Date fimVigencia) {
        this.fimVigencia = fimVigencia;
    }
}
