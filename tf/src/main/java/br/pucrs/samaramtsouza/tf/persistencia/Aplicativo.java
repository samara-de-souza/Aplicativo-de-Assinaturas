package br.pucrs.samaramtsouza.tf.persistencia;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Aplicativo {
    @Id
    private Long codigo;
    private String nome;
    private double custoMensal;

    public Aplicativo() {}

    public Aplicativo(Long codigo, String nome, double custoMensal) {
        this.codigo = codigo;
        this.nome = nome;
        this.custoMensal = custoMensal;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getCustoMensal() {
        return custoMensal;
    }

    public void setCustoMensal(double custoMensal) {
        this.custoMensal = custoMensal;
    }
}
