package br.com.bb3soft.loja.exception.handler;

import br.com.bb3soft.loja.exception.FormaPagamentoNaoEncontradoException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class FormaPagamentoNaoEncontradoExceptionHandler implements
        ExceptionMapper<FormaPagamentoNaoEncontradoException> {

    @Override
    public Response toResponse(FormaPagamentoNaoEncontradoException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }

}
