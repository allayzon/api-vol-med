package med.voll.api.domain.usuario;

import med.voll.api.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    UserDetails findByLogin(String login);
}