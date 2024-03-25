package br.com.bb3soft.loja.resource;

import br.com.bb3soft.loja.dto.UsuarioDTO;
import br.com.bb3soft.loja.request.UsuarioRequest;
import br.com.bb3soft.loja.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private UsuarioService usuarioService;

    @Inject
    public UsuarioResource(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @POST
    public Response salvar(@Valid UsuarioRequest usuarioRequest) {
        var usuarioDTO = usuarioService.salvar(usuarioRequest);
        return Response.status(Response.Status.CREATED).entity(usuarioDTO).build();
    }

    @PUT
    @Path("/{usuarioId}")
    public UsuarioDTO editar(@PathParam("usuarioId") Long usuarioId, @Valid UsuarioRequest usuarioRequest) {
        return usuarioService.editar(usuarioId, usuarioRequest);
    }

    @GET
    @Path("/{usuarioId}")
    public UsuarioDTO buscarPorId(@PathParam("usuarioId") Long usuarioId) {
        return usuarioService.buscarPorId(usuarioId);
    }

    @GET
    public List<UsuarioDTO> listar() {
        return usuarioService.listar();
    }

    @DELETE
    @Path("/{usuarioId}")
    public Response remover(@PathParam("usuarioId") Long usuarioId) {
        usuarioService.excluir(usuarioId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/app/{usuarioId}")
    public void removerUsuarioApp(@PathParam("usuarioId") Long usuarioId) {
        usuarioService.excluir(usuarioId);
    }

    @PUT
    @Path("/inativar/{usuarioId}")
    public Response inativar(@PathParam("usuarioId") Long usuarioId) {
        usuarioService.inativar(usuarioId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("/ativar/{usuarioId}")
    public Response ativar(@PathParam("usuarioId") Long usuarioId) {
        usuarioService.ativar(usuarioId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
