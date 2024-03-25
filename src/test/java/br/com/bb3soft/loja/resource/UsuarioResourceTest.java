package br.com.bb3soft.loja.resource;

import br.com.bb3soft.loja.dto.UsuarioDTO;
import br.com.bb3soft.loja.enums.Situacao;
import br.com.bb3soft.loja.request.UsuarioRequest;
import br.com.bb3soft.loja.service.UsuarioService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@QuarkusTest
class UsuarioResourceTest {


    @Mock
    UsuarioService usuarioService;

    @InjectMocks
    UsuarioResource usuarioResource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testar o endpoint do usuario")
    void deve_savar_usuario_com_sucesso() {
        var usuarioRequest = UsuarioRequest.builder()
                .nome("Nayla")
                .senha("nayla123")
                .email("naylabercalini@hotmail.com")
                .build();

        var usuarioDTO = new UsuarioDTO(1L, "Murilo", "murilobercalini@hotmail.com",
                "murilo123", Situacao.ATIVO, LocalDateTime.now(), LocalDateTime.now());

        when(usuarioService.salvar(usuarioRequest)).thenReturn(usuarioDTO);

        Response response = usuarioResource.salvar(usuarioRequest);
        UsuarioDTO usuarioSalvo = response.readEntity(UsuarioDTO.class);

        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        assertEquals(1L, usuarioSalvo.id());
        assertEquals("Murilo", usuarioSalvo.nome());
        assertEquals("murilobercalini@hotmail.com", usuarioSalvo.email());

        verify(usuarioService, times(1)).salvar(usuarioRequest);
    }

    @Test
    @DisplayName("Testar o endpoint de atualizar usuario")
    void atualizar_usuario_com_sucesso() {
        var usuarioRequest = UsuarioRequest.builder()
                .nome("Nayla")
                .senha("nayla123")
                .email("naylabercalini@hotmail.com")
                .build();

        var usuarioDTO = new UsuarioDTO(1L, "Murilo", "murilobercalini@hotmail.com",
                "murilo123", Situacao.ATIVO, LocalDateTime.now(), LocalDateTime.now());

        when(usuarioService.editar(1L, usuarioRequest)).thenReturn(usuarioDTO);
        var response = usuarioResource.editar(1L, usuarioRequest);

        assertNotNull(response);
        assertEquals(200, Response.Status.OK.getStatusCode());
        assertEquals(UsuarioDTO.class, response.getClass());
        assertEquals("Murilo", response.nome());
        assertEquals("murilobercalini@hotmail.com", response.email());

        verify(usuarioService, times(1)).editar(1L, usuarioRequest);
    }

    @Test
    @DisplayName("Testar o endpoint de buscar por id")
    void buscar_usuario_por_id() {
        var usuarioDTO = new UsuarioDTO(1L, "Murilo", "murilobercalini@hotmail.com",
                "murilo123", Situacao.ATIVO, LocalDateTime.now(), LocalDateTime.now());

        when(usuarioService.buscarPorId(1L)).thenReturn(usuarioDTO);
        var response = usuarioResource.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(200, Response.Status.OK.getStatusCode());
        assertEquals("Murilo", response.nome());
        assertEquals("murilobercalini@hotmail.com", response.email());

        verify(usuarioService, times(1)).buscarPorId(1L);
    }

    @Test
    @DisplayName("Testar o endpoint de listar usuario")
    void listar_usuario_com_sucesso() {
        List<UsuarioDTO> usuarioDTOS = Arrays.asList(new UsuarioDTO(1L, "Murilo",
                "murilobercalini@hotmail.com", "murilo123", Situacao.ATIVO,
                LocalDateTime.now(), LocalDateTime.now()));

        when(usuarioService.listar()).thenReturn(usuarioDTOS);
        List<UsuarioDTO> response = usuarioResource.listar();

        assertNotNull(response);
        assertEquals(200, Response.Status.OK.getStatusCode());
        assertEquals("Murilo", response.get(0).nome());
        assertEquals("murilobercalini@hotmail.com", response.get(0).email());

        verify(usuarioService, times(1)).listar();
    }

    @Test
    @DisplayName("Testar o endpoint de remover usuario")
    void remover_com_sucesso() {
        doNothing().when(usuarioService).excluir(1L);

        Response response = usuarioResource.remover(1L);
        assertEquals(204, response.getStatus());

        verify(usuarioService, times(1)).excluir(1L);
    }

    @Test
    @DisplayName("Testar o endpoint de inativar")
    void inativar_com_sucesso() {
        doNothing().when(usuarioService).inativar(1L);

        usuarioResource.inativar(1L);
        assertEquals(204, Response.Status.NO_CONTENT.getStatusCode());
        verify(usuarioService, times(1)).inativar(1L);
    }

    @Test
    @DisplayName("Testar o endpoint de ativar")
    void ativar_com_sucesso() {
        doNothing().when(usuarioService).ativar(1L);

        usuarioResource.ativar(1L);
        assertEquals(204, Response.Status.NO_CONTENT.getStatusCode());
        verify(usuarioService, times(1)).ativar(1L);
    }

}