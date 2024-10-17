package br.AppAssinatura.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAplicativoRepository extends JpaRepository<Aplicativo, Long> {
    
}
