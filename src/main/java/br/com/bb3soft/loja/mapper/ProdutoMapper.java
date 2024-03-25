package br.com.bb3soft.loja.mapper;

import br.com.bb3soft.loja.dto.ProdutoDTO;
import br.com.bb3soft.loja.model.Produto;
import br.com.bb3soft.loja.request.ProdutoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface ProdutoMapper {

    @Mapping(target = "id", ignore = true)
    Produto produtoRequestToProduto(ProdutoRequest produtoRequest);

    ProdutoDTO produtoTODTO(Produto produto);

}
