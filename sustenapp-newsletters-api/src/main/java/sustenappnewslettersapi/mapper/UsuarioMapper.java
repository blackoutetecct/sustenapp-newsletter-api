package sustenappnewslettersapi.mapper;

import org.springframework.beans.BeanUtils;
import sustenappnewslettersapi.dto.UsuarioDto;
import sustenappnewslettersapi.model.UsuarioModel;

public class UsuarioMapper {
    public static UsuarioModel toMapper(UsuarioDto objetoEntrada) {
        UsuarioModel objetoSaida = new UsuarioModel();
        BeanUtils.copyProperties(objetoEntrada, objetoSaida);
        return objetoSaida;
    }
}
