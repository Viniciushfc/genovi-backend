package br.com.genovi.application.infrastructure.repositories;

import br.com.genovi.application.domain.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
