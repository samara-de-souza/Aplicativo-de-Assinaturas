package br.pucrs.samaramtsouza.tf.persistencia;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAssinaturaRepository extends JpaRepository<Assinatura, Long> {
    List<Assinatura> findByCliente(Cliente cliente);
    List<Assinatura> findByAplicativo(Aplicativo aplicativo);
}