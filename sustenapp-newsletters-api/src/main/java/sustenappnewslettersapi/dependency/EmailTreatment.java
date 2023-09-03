package sustenappnewslettersapi.dependency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailTreatment {
    private final FileDependency fileDependency;

    public String getBody(String body) {
        return fileDependency.read("email.txt").replace("[BODY]", body);
    }
}
