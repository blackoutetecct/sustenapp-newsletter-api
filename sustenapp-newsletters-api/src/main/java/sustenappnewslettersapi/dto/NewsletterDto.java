package sustenappnewslettersapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsletterDto {
    @NotNull @NotEmpty
    String assunto, texto;
}