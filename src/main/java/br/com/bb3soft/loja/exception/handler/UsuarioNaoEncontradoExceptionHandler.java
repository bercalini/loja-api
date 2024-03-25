package br.com.bb3soft.loja.exception.handler;

import br.com.bb3soft.loja.exception.UsuarioNaoEnconradoException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UsuarioNaoEncontradoExceptionHandler implements ExceptionMapper<UsuarioNaoEnconradoException> {

    @Override
    public Response toResponse(UsuarioNaoEnconradoException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }

}
