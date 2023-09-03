package sustenappnewslettersapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sustenappnewslettersapi.dto.UsuarioDto;
import sustenappnewslettersapi.exception.ResponseBody;
import sustenappnewslettersapi.service.UsuarioService;

@RestController
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping("/usuario")
    public ResponseEntity<ResponseBody> save(@RequestBody UsuarioDto usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }
}
