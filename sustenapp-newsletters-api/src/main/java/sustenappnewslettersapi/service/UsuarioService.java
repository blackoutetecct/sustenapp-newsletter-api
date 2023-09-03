package sustenappnewslettersapi.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustenappnewslettersapi.dependency.EmailDependency;
import sustenappnewslettersapi.dto.EmailDto;
import sustenappnewslettersapi.dto.UsuarioDto;
import sustenappnewslettersapi.exception.ExceptionGeneric;
import sustenappnewslettersapi.exception.ResponseBody;
import sustenappnewslettersapi.mapper.UsuarioMapper;
import sustenappnewslettersapi.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EmailDependency emailDependency;

    @Transactional(rollbackFor = ExceptionGeneric.class)
    public ResponseBody save(@Valid UsuarioDto usuarioDto){
        existsUsuario(usuarioDto.getEmail());
        sendEmailNewUser(usuarioRepository.save(UsuarioMapper.toMapper(usuarioDto)).getEmail());

        return getResponse("USUARIO CADASTRADO", "USUARIO ESTA REGISTRADO PARA O RECEBIMENTO DE NEWSLETTER", 201);
    }

    private void existsUsuario(String email) {
        if(usuarioRepository.existsByEmail(email))
            throw new ExceptionGeneric("USUARIO EXISTENTE", "USUARIO JA ESTA REGISTRADO PARA O RECEBIMENTO DE NEWSLETTER", 409);
    }

    private ResponseBody getResponse(String titulo, String mensagem, int status) {
        return ResponseBody
                .builder()
                .title(titulo)
                .message(mensagem)
                .status(status)
                .build();
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
}
