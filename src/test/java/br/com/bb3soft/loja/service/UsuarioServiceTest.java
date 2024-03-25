package br.com.bb3soft.loja.service;

import br.com.bb3soft.loja.dto.UsuarioDTO;
import br.com.bb3soft.loja.enums.Situacao;
import br.com.bb3soft.loja.exception.UsuarioNaoEnconradoException;
import br.com.bb3soft.loja.mapper.UsuarioMapper;
import br.com.bb3soft.loja.model.Usuario;
import br.com.bb3soft.loja.repository.UsuarioRepository;
import br.com.bb3soft.loja.request.UsuarioRequest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class UsuarioServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    UsuarioMapper usuarioMapper;

    @InjectMocks
    UsuarioService usuarioService;

    final static String USUARIO_NAO_ENCONTRADO = "Usuario com id : %s não encontrado";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve salvar um usuario com sucesso")
    void salvar_usuario_com_sucesso() {
        var usuario = Usuario.builder()
                .id(1L)
                .nome("Murilo")
                .senha("murilo123")
                .email("murilobercalini@hotmail.com")
                .situacao(Situacao.ATIVO)
                .build();

        var usuarioRequest = UsuarioRequest.builder()
                .nome("Nayla")
                .senha("nayla123")
                .email("naylabercalini@hotmail.com")
                .build();

        var usuarioDTO = new UsuarioDTO(1L, "Murilo", "murilobercalini@hotmail.com",
                "murilo123", Situacao.ATIVO, LocalDateTime.now(), LocalDateTime.now());

        when(usuarioMapper.usuarioRequestTOUsuario(usuarioRequest)).thenReturn(usuario);
        when(usuarioMapper.usuarioTOUsuarioDTO(usuario)).thenReturn(usuarioDTO);

        var result = usuarioService.salvar(usuarioRequest);

        assertNotNull(result);
        assertEquals(UsuarioDTO.class, result.getClass());
        assertEquals(usuarioDTO.nome(), result.nome());
        assertEquals("Murilo", result.nome());
        assertEquals("murilobercalini@hotmail.com", result.email());
    }

    @Test
    @DisplayName("Deve atualizar um usuario com sucesso")
    void atualizar_usuario_com_sucesso() {
        var usuario = Usuario.builder()
                .id(1L)
                .nome("Murilo")
                .senha("murilo123")
                .email("murilobercalini@hotmail.com")
                .situacao(Situacao.ATIVO)
                .build();

        var usuarioRequest = UsuarioRequest.builder()
                .nome("Nayla")
                .senha("nayla123")
                .email("naylabercalini@hotmail.com")
                .build();

        var usuarioDTO = new UsuarioDTO(1L, "Murilo", "murilobercalini@hotmail.com",
                "murilo123", Situacao.ATIVO, LocalDateTime.now(), LocalDateTime.now());

        when(usuarioRepository.findByIdOptional(1L)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.usuarioTOUsuarioDTO(usuario)).thenReturn(usuarioDTO);

        var result = usuarioService.editar(1L, usuarioRequest);
        assertNotNull(result);
        assertEquals(UsuarioDTO.class, result.getClass());
        assertEquals("Murilo", result.nome());
        assertEquals("murilobercalini@hotmail.com", result.email());
    }

    @Test
    @DisplayName("Deve lançar um erro ao passar um usuario invalido")
    void relizar_erro_ao_atulizar_usuario_invalido() {
        var usuarioRequest = UsuarioRequest.builder()
                .nome("Nayla")
                .senha("nayla123")
                .email("naylabercalini@hotmail.com")
                .build();

        var ex = assertThrows(UsuarioNaoEnconradoException.class, () -> {
            usuarioService.editar(1L, usuarioRequest);
        });

        assertEquals(String.format(ex.getMessage(), 1L), String.format(USUARIO_NAO_ENCONTRADO, 1L));
    }

    @Test
    @DisplayName("Deve exluir um usuario com sucesso")
    void excluir_usuario_com_sucesso() {
        var usuario = Usuario.builder()
                .id(1L)
                .nome("Murilo")
                .senha("murilo123")
                .email("murilobercalini@hotmail.com")
                .situacao(Situacao.ATIVO)
                .build();

        when(usuarioRepository.findByIdOptional(1L)).thenReturn(Optional.of(usuario));
        usuarioService.excluir(1L);
        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    @DisplayName("Deve ocorrer erro ao tentar não excluir usuario sem id")
    void erro_ao_excluir_usuario_nao_encontrado() {
        var ex = assertThrows(UsuarioNaoEnconradoException.class, () -> {
            usuarioService.excluir(1L);
        });

        assertEquals(String.format(ex.getMessage(), 1L), String.format(USUARIO_NAO_ENCONTRADO, 1L));
    }

    @Test
    @DisplayName("Deve ocorrer erro ao tentar buscar usuario sem id")
    void erro_ao_buscar_usuario_nao_encontrado() {
        var ex = assertThrows(UsuarioNaoEnconradoException.class, () -> {
            usuarioService.buscarPorId(1L);
        });

        assertEquals(String.format(ex.getMessage(), 1L), String.format(USUARIO_NAO_ENCONTRADO, 1L));
    }

    @Test
    @DisplayName("Deve buscar usuario com sucesso")
    void buscar_usuario_com_sucesso() {
        var usuario = Usuario.builder()
                .id(1L)
                .nome("Murilo")
                .senha("murilo123")
                .email("murilobercalini@hotmail.com")
                .situacao(Situacao.ATIVO)
                .build();

        var usuarioDTO = new UsuarioDTO(1L, "Murilo", "murilobercalini@hotmail.com",
                "murilo123", Situacao.ATIVO, LocalDateTime.now(), LocalDateTime.now());

        when(usuarioRepository.findByIdOptional(1l)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.usuarioTOUsuarioDTO(usuario)).thenReturn(usuarioDTO);

        var response = usuarioService.buscarPorId(1L);
        assertNotNull(response);
        assertEquals(UsuarioDTO.class, response.getClass());
        assertEquals("Murilo", response.nome());
        assertEquals("murilobercalini@hotmail.com", response.email());
        assertEquals("murilo123", response.senha());
    }

    @Test
    @DisplayName("Deve buscar uma lista de usuario")
    void deve_realizar_lista_usuarios() {
        List<Usuario> usuarios = Arrays.asList(Usuario.builder().id(1L).nome("Murilo").senha("murilo123")
                .email("murilobercalini@hotmail.com").situacao(Situacao.ATIVO).build(),
                Usuario.builder().id(2L).nome("Alisson").senha("alisson123")
                        .email("murilobercalini@hotmail.com").situacao(Situacao.ATIVO).build());

        var usuarioDTO = new UsuarioDTO(1L, "Murilo", "murilobercalini@hotmail.com",
                "murilo123", Situacao.ATIVO, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    @DisplayName("deve inativar usuario")
    void inativar_usuario() {
        var usuario = Usuario.builder()
                .id(1L)
                .nome("Murilo")
                .senha("murilo123")
                .email("murilobercalini@hotmail.com")
                .situacao(Situacao.ATIVO)
                .build();

        when(usuarioRepository.findByIdOptional(1L)).thenReturn(Optional.of(usuario));
        usuarioService.inativar(1L);

        verify(usuarioRepository, times(1)).findByIdOptional(1L);
        assertEquals(Situacao.INATIVO, usuario.getSituacao());
    }

    @Test
    @DisplayName("deve ATIVAR usuario")
    void ativar_usuario() {
        var usuario = Usuario.builder()
                .id(1L)
                .nome("Murilo")
                .senha("murilo123")
                .email("murilobercalini@hotmail.com")
                .situacao(Situacao.INATIVO)
                .build();

        when(usuarioRepository.findByIdOptional(1L)).thenReturn(Optional.of(usuario));
        usuarioService.ativar(1L);

        verify(usuarioRepository, times(1)).findByIdOptional(1L);
        assertEquals(Situacao.ATIVO, usuario.getSituacao());
    }
}