package sustenappnewslettersapi.dependency;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@RequiredArgsConstructor
public class FileDependency {
    private final ResourceLoader resourceLoader;

    public String read(String endereco) {
        StringBuilder arquivo = new StringBuilder();

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(
                        resourceLoader.getResource("classpath:templates/" + endereco).getInputStream()))
        ){
            String linha;

            while ((linha = leitor.readLine()) != null) {
                arquivo.append(linha);
            }
        } catch (Exception ignored) {
            return null;
        }

        return arquivo.toString();
    }
}

