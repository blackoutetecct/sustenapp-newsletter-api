package sustenappnewslettersapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDto {
    @NotNull @NotEmpty @Email
    String destinatario;

    @NotNull @NotEmpty
    String assunto, texto;
}