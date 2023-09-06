package sustenappnewslettersapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustenappnewslettersapi.component.rule.Validation;
import sustenappnewslettersapi.component.validation.NotExistsEmail;
import sustenappnewslettersapi.component.validation.EmailValidation;
import sustenappnewslettersapi.component.validation.NotEmpty;
import sustenappnewslettersapi.component.validation.NotNull;
import sustenappnewslettersapi.dependency.EmailDependency;
import sustenappnewslettersapi.dto.EmailDto;
import sustenappnewslettersapi.dto.UsuarioDto;
import sustenappnewslettersapi.exception.ExceptionGeneric;
import sustenappnewslettersapi.exception.ResponseBody;
import sustenappnewslettersapi.mapper.UsuarioMapper;
import sustenappnewslettersapi.repository.UsuarioRepository;

import java.util.stream.Stream;

import static sustenappnewslettersapi.component.util.FactoryResponseBody.getResponse;

@Service
@RequiredArgsConstructor
public class UsuarioService implements Validation<UsuarioDto> {
    private final UsuarioRepository usuarioRepository;
    private final EmailDependency emailDependency;
    private final NotExistsEmail emailExists;

    @Transactional(rollbackFor = ExceptionGeneric.class)
    public ResponseBody save(UsuarioDto usuarioDto){
        validated(usuarioDto);

        sendEmailNewUser(usuarioRepository.save(UsuarioMapper.toMapper(usuarioDto)).getEmail());
        return getResponse("USUARIO CADASTRADO", "USUARIO ESTA REGISTRADO PARA O RECEBIMENTO DE NEWSLETTER", 201);
    }

    private void sendEmailNewUser(String email) {
        emailDependency.sendEmail(
                EmailDto
                        .builder()
                        .destinatario(email)
                        .assunto("")
                        .texto("")
                        .build()
        );
    }

    @Override
    public boolean validate(UsuarioDto value) {
        return Stream.of(
                NotNull.isValid(value.getEmail()),
                NotEmpty.isValid(value.getEmail()),
                EmailValidation.isValid(value.getEmail()),
                emailExists.isValid(value.getEmail())
        ).allMatch(valor -> valor.equals(true));
    }

    @Override
    public void validated(UsuarioDto value) {
        if(!validate(value))
            throw new ExceptionGeneric("", "", 400);
    }
}
