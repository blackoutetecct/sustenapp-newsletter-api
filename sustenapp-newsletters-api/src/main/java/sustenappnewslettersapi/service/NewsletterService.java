package sustenappnewslettersapi.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import sustenappnewslettersapi.dependency.EmailDependency;
import sustenappnewslettersapi.dto.EmailDto;
import sustenappnewslettersapi.dto.NewsletterDto;
import sustenappnewslettersapi.exception.ExceptionGeneric;
import sustenappnewslettersapi.exception.ResponseBody;
import sustenappnewslettersapi.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class NewsletterService {
    private final EmailDependency emailDependency;
    private final UsuarioRepository usuarioRepository;

    public ResponseBody sendNewsletter(@Valid NewsletterDto newsletterDto) {
        usuarioRepository
                .findAll()
                .forEach(usuario -> {
                    sendEmail(
                            EmailDto
                                    .builder()
                                    .destinatario(usuario.getEmail())
                                    .assunto(newsletterDto.getAssunto())
                                    .texto(newsletterDto.getTexto())
                                    .build()
                    );
                });

        return ResponseBody
                .builder()
                .title("ENVIO DE NEWSLETTER CONCLUIDO")
                .message("ENVIO DE NEWSLETTER CONCLUIDO")
                .status(200)
                .build();
    }

    @Retryable(value = ExceptionGeneric.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    private void sendEmail(EmailDto email) {
        emailDependency.sendEmail(email);
    }
}
