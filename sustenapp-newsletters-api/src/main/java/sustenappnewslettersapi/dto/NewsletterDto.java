package sustenappnewslettersapi.dto;

import lombok.Data;

@Data
public class NewsletterDto {
    String token, assunto, texto;
}