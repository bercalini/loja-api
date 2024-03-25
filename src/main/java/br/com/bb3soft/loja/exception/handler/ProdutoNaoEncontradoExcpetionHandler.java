package br.com.bb3soft.loja.exception.handler;

import br.com.bb3soft.loja.exception.ProdutoNaoEncontradoExecption;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ProdutoNaoEncontradoExcpetionHandler implements ExceptionMapper<ProdutoNaoEncontradoExecption> {

    @Override
    public Response toResponse(ProdutoNaoEncontradoExecption e) {
        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }

}
