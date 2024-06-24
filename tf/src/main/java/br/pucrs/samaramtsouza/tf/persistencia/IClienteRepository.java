package br.pucrs.samaramtsouza.tf.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<Cliente, Long> {
    
}
