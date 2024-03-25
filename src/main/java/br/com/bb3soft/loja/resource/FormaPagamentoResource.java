package br.com.bb3soft.loja.resource;

import br.com.bb3soft.loja.model.FormaPagamento;
import br.com.bb3soft.loja.service.FormaPagamentoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/formapagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FormaPagamentoResource {

    private FormaPagamentoService formaPagamentoService;

    @Inject
    public FormaPagamentoResource(FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = formaPagamentoService;
    }

    @GET
    public List<FormaPagamento> listar() {
        return formaPagamentoService.listar();
    }

    @GET
    @Path("/{pagamentoId}")
    public FormaPagamento buscarPorId(@PathParam("pagamentoId") Long pagamentoId) {
        return formaPagamentoService.buscarPorId(pagamentoId);
    }

}
