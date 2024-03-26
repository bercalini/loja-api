package br.com.bb3soft.loja.resource;

import br.com.bb3soft.loja.model.FormaPagamento;
import br.com.bb3soft.loja.service.FormaPagamentoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

    @POST
    public Response salvarPagamento(@Valid FormaPagamento formaPagamento) {
        formaPagamentoService.salvar(formaPagamento);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public List<FormaPagamento> listar() {
        return formaPagamentoService.listar();
    }

    @GET
    @Path("/{pagamentoId}")
    public FormaPagamento buscarPorId(@PathParam("pagamentoId") Long pagamentoId) {
        System.out.println("aquiiiiiiiiii");
      return formaPagamentoService.buscarPorId(pagamentoId);
    }

}
