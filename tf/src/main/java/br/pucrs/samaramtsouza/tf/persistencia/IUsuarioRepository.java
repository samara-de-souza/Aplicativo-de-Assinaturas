package br.pucrs.samaramtsouza.tf.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, String> {
}
