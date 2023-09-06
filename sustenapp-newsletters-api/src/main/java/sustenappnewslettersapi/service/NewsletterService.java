package sustenappnewslettersapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import sustenappnewslettersapi.component.rule.Validation;
import sustenappnewslettersapi.component.validation.NotEmpty;
import sustenappnewslettersapi.component.validation.NotNull;
import sustenappnewslettersapi.component.validation.TokenValidation;
import sustenappnewslettersapi.dependency.EmailDependency;
import sustenappnewslettersapi.dto.EmailDto;
import sustenappnewslettersapi.dto.NewsletterDto;
import sustenappnewslettersapi.exception.ExceptionGeneric;
import sustenappnewslettersapi.exception.ResponseBody;
import sustenappnewslettersapi.repository.UsuarioRepository;

import java.util.stream.Stream;

import static sustenappnewslettersapi.component.util.FactoryResponseBody.getResponse;

@Service
@RequiredArgsConstructor
public class NewsletterService implements Validation<NewsletterDto> {
    private final EmailDependency emailDependency;
    private final UsuarioRepository usuarioRepository;
    private final TokenValidation tokenValidation;

    public ResponseBody sendNewsletter(NewsletterDto newsletterDto) {
        validate(newsletterDto);

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

        return getResponse("ENVIO DE NEWSLETTER CONCLUIDO", "ENVIO DE NEWSLETTER CONCLUIDO", 200);
    }

    @Retryable(value = ExceptionGeneric.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    private void sendEmail(EmailDto email) {
        emailDependency.sendEmail(email);
    }

    @Override
    public boolean validate(NewsletterDto value) {
        return Stream.of(
                NotNull.isValid(value.getAssunto()),
                NotNull.isValid(value.getTexto()),
                NotEmpty.isValid(value.getAssunto()),
                NotEmpty.isValid(value.getTexto()),
                tokenValidation.isValid(value.getToken())
        ).allMatch(valor -> valor.equals(true));
    }

    @Override
    public void validated(NewsletterDto value) {
        if(!validate(value))
            throw new ExceptionGeneric("", "", 400);
    }
}
