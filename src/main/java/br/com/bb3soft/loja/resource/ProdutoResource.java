package br.com.bb3soft.loja.resource;

import br.com.bb3soft.loja.dto.ProdutoDTO;
import br.com.bb3soft.loja.request.ProdutoRequest;
import br.com.bb3soft.loja.service.ProdutoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/produtos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    private ProdutoService produtoService;

    @Inject
    public ProdutoResource(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @POST
    public Response salvar(@Valid ProdutoRequest produtoRequest) {
        var produtoDTO = produtoService.salvar(produtoRequest);
        return Response.status(Response.Status.CREATED).entity(produtoDTO).build();
    }

    @PUT
    @Path("/{produtoId}")
    public ProdutoDTO atualizar(@PathParam("produtoId") Long produtoId, @Valid ProdutoRequest produtoRequest) {
        return produtoService.atualizar(produtoId, produtoRequest);
    }

    @DELETE
    @Path("/{produtoId}")
    public Response excluir(@PathParam("produtoId") Long produtoId) {
        produtoService.remover(produtoId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{produtoId}")
    public ProdutoDTO buscarPorId(@PathParam("produtoId") Long produtoId) {
        return produtoService.buscarPorId(produtoId);
    }

    @GET
    public List<ProdutoDTO> listar() {
        return produtoService.listar();
    }

}
