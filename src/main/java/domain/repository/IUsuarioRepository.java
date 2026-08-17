package domain.repository;

import domain.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, String> {
}
