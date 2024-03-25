package br.com.bb3soft.loja.mapper;

import br.com.bb3soft.loja.dto.UsuarioDTO;
import br.com.bb3soft.loja.model.Usuario;
import br.com.bb3soft.loja.request.UsuarioRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    Usuario usuarioRequestTOUsuario(UsuarioRequest usuarioRequest);

    UsuarioDTO usuarioTOUsuarioDTO(Usuario usuario);
}
