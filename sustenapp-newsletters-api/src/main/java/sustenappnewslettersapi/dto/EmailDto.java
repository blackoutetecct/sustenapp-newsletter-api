package sustenappnewslettersapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDto {
    String destinatario;
    String assunto, texto;
}