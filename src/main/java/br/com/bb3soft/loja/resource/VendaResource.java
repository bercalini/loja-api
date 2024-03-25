package br.com.bb3soft.loja.resource;

import br.com.bb3soft.loja.dto.VendaDTO;
import br.com.bb3soft.loja.model.Venda;
import br.com.bb3soft.loja.request.VendaRequest;
import br.com.bb3soft.loja.service.VendaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/vendas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VendaResource {

    private VendaService vendaService;

    @Inject
    public VendaResource(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @POST
    public void realizarVenda(@Valid VendaRequest vendaRequest) {
        vendaService.realizarVenda(vendaRequest);
    }

    @GET
    public List<VendaDTO> listarVendas() {
        return vendaService.listarVendas();
    }

    @GET
    @Path("/teste")
    public List<Venda> listarVendasTeste() {
        return vendaService.listarVendasTeste();
    }
}
