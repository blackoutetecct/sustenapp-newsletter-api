package sustenappnewslettersapi.component.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenValidation {
    @Value("${admin.token}") private String token;

    public boolean isValid(String value) {
        return value.equals(token.toString());
    }
}
