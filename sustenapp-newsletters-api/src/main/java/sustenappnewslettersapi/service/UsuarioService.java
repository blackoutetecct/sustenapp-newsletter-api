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
                        .assunto("Serviço de Newsletter")
                        .texto("""
                                   Olá, <br><br>Esperamos que esta mensagem o encontre bem. Gostaríamos de expressar nossa sincera gratidão pelo seu recente cadastro em nossa newsletter. É um prazer tê-lo(a) conosco!<br>Ao se inscrever, você está prestes a receber informações exclusivas, atualizações emocionantes e conteúdos relevantes diretamente em sua caixa de entrada. Estamos empolgados em compartilhar as últimas novidades, dicas úteis e ofertas especiais que certamente agregarão valor à sua experiência.<br>Acreditamos que a comunicação é a chave para construir uma relação sólida, e estamos comprometidos em fornecer conteúdo interessante e informativo que atenda às suas expectativas. Se houver temas específicos que você gostaria de ver abordados ou se tiver algum feedback para compartilhar, não hesite em nos informar. Sua opinião é valiosa para nós.<br>Fique à vontade para explorar nosso site e descobrir mais sobre os produtos/serviços que oferecemos. Caso tenha alguma dúvida ou precise de assistência, nossa equipe de suporte está sempre pronta para ajudar.<br>Agradecemos mais uma vez por escolher se juntar à nossa comunidade através da assinatura de nossa newsletter. Estamos ansiosos para manter uma conexão significativa e enriquecedora.<br><br>Seja bem-vindo(a)!
                               """)
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
             throw new ExceptionGeneric("INFORMAÇÕES INCOMPATIVIES COM O ESPERADO", "INFORMAÇÕES INCOMPATIVIES COM O ESPERADO", 400);
    }
}
