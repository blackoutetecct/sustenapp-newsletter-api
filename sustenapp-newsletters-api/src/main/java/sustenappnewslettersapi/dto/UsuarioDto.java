package sustenappnewslettersapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioDto {
    @NotNull @NotEmpty @Email
    String email;
}