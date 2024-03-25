package br.com.bb3soft.loja.resource;

import br.com.bb3soft.loja.dto.ProdutoDTO;
import br.com.bb3soft.loja.request.ProdutoRequest;
import br.com.bb3soft.loja.service.ProdutoService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@QuarkusTest
class ProdutoResourceTest {

    @Mock
    ProdutoService produtoService;

    @InjectMocks
    ProdutoResource produtoResource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("deve testar o end point do produto pra salvar")
    void testar_endpoint_pra_salvar() {
        var produtoRequest = ProdutoRequest.builder()
                .nome("Celular novo")
                .descricao("Celular novo na caixa")
                .valor(new BigDecimal("2000"))
                .quantidadeEstoque(20)
                .build();

        var produtoDTO = new ProdutoDTO(1L, "mouse", "mouse novo", new BigDecimal("40"),
                15, LocalDateTime.now(), LocalDateTime.now());

        when(produtoService.salvar(produtoRequest)).thenReturn(produtoDTO);
        var response = produtoResource.salvar(produtoRequest);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        verify(produtoService, times(1)).salvar(produtoRequest);
    }

    @Test
    @DisplayName("deve testar o end point do produto pra buscar por id")
    void testar_endpoint_pra_buscar_por_id() {
        var produtoDTO = new ProdutoDTO(1L, "mouse", "mouse novo", new BigDecimal("40"),
                15, LocalDateTime.now(), LocalDateTime.now());

        when(produtoService.buscarPorId(1L)).thenReturn(produtoDTO);
        var response = produtoResource.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(ProdutoDTO.class, response.getClass());
        assertEquals("mouse", response.nome());
        assertEquals("mouse novo", response.descricao());
        assertEquals(Response.Status.OK.getStatusCode(), 200);

        verify(produtoService, times(1)).buscarPorId(1L);
    }

    @Test
    @DisplayName("deve testar o end point do produto pra atualizar")
    void testar_endpoint_pra_atualizar() {
        var produtoRequest = ProdutoRequest.builder()
                .nome("Celular novo")
                .descricao("Celular novo na caixa")
                .valor(new BigDecimal("2000"))
                .quantidadeEstoque(20)
                .build();

        var produtoDTO = new ProdutoDTO(1L, "mouse", "mouse novo", new BigDecimal("40"),
                15, LocalDateTime.now(), LocalDateTime.now());

        when(produtoService.atualizar(1L, produtoRequest)).thenReturn(produtoDTO);
        var response = produtoResource.atualizar(1L, produtoRequest);

        assertNotNull(response);
        assertEquals(ProdutoDTO.class, response.getClass());
        assertEquals("mouse", response.nome());
        assertEquals("mouse novo", response.descricao());
        assertEquals(Response.Status.OK.getStatusCode(), 200);

        verify(produtoService, times(1)).atualizar(1L, produtoRequest);
    }

    @Test
    @DisplayName("deve testar o end point do produto pra remover")
    void testar_endpoint_pra_remover() {
        doNothing().when(produtoService).remover(1L);
        Response response = produtoResource.excluir(1L);

        assertNotNull(response);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), 204);
        verify(produtoService, times(1)).remover(1L);
    }

    @Test
    @DisplayName("deve testar o end point do produto pra listar")
    void testar_endpoint_pra_listar() {
        List<ProdutoDTO> produtoDTOS = Arrays.asList(new ProdutoDTO(1L, "mouse",
                "mouse novo", new BigDecimal("40"), 15,
                LocalDateTime.now(), LocalDateTime.now()), new ProdutoDTO(2L, "notbook",
                "notbook", new BigDecimal("3500"), 5,
                LocalDateTime.now(), LocalDateTime.now()));

        when(produtoService.listar()).thenReturn(produtoDTOS);
        List<ProdutoDTO> listar = produtoResource.listar();

        assertNotNull(200);
        assertEquals(Response.Status.OK.getStatusCode(), 200);
        assertEquals(2, listar.size());
    }


}