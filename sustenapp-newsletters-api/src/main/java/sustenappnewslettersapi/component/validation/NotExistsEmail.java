package sustenappnewslettersapi.component.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sustenappnewslettersapi.repository.UsuarioRepository;

@Component
@RequiredArgsConstructor
public class NotExistsEmail {
    private final UsuarioRepository usuarioRepository;

    public boolean isValid(String value) {
        return existsUsuario(value);
    }

    private boolean existsUsuario(String email) {
        return !usuarioRepository.existsByEmail(email);
    }
}
