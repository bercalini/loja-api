package br.com.bb3soft.loja.mapper;

import br.com.bb3soft.loja.dto.ClienteDTO;
import br.com.bb3soft.loja.model.Cliente;
import br.com.bb3soft.loja.request.ClienteRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface ClienteMapper {

    @Mapping(target = "id", ignore = true)
    Cliente clienteRequestToCliente(ClienteRequest clienteRequest);

    ClienteDTO clienteTODTO(Cliente cliente);
}
