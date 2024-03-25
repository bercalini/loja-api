package br.com.bb3soft.loja.resource;

import br.com.bb3soft.loja.dto.ClienteDTO;
import br.com.bb3soft.loja.request.ClienteRequest;
import br.com.bb3soft.loja.service.ClienteService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    private ClienteService clienteService;

    @Inject
    public ClienteResource(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @POST
    public Response salvar(@Valid ClienteRequest clienteRequest) {
        var clienteDTO = clienteService.salvar(clienteRequest);
        return Response.status(Response.Status.CREATED).entity(clienteDTO).build();
    }

    @GET
    @Path("/{clienteId}")
    public ClienteDTO buscarPorId(@PathParam("clienteId") Long clienteId) {
        return clienteService.buscarPorId(clienteId);
    }

    @GET
    public List<ClienteDTO> listar() {
        return clienteService.listarTodos();
    }

    @PUT
    @Path("/{clienteId}")
    public ClienteDTO atualizar(@PathParam("clienteId") Long clienteId, @Valid ClienteRequest clienteRequest) {
        return clienteService.atualizar(clienteId, clienteRequest);
    }

    @DELETE
    @Path("/{clienteId}")
    public Response remover(@PathParam("clienteId") Long clienteId) {
        clienteService.remover(clienteId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
