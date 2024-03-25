package br.com.bb3soft.loja.exception.handler;

import br.com.bb3soft.loja.exception.ClienteNaoEncontradoException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ClienteNaoEncontradoExceptionHandler implements ExceptionMapper<ClienteNaoEncontradoException> {

    @Override
    public Response toResponse(ClienteNaoEncontradoException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }

}
