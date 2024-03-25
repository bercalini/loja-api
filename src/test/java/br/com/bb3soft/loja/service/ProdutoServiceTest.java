package br.com.bb3soft.loja.service;

import br.com.bb3soft.loja.dto.ProdutoDTO;
import br.com.bb3soft.loja.exception.ProdutoNaoEncontradoExecption;
import br.com.bb3soft.loja.mapper.ProdutoMapper;
import br.com.bb3soft.loja.model.Produto;
import br.com.bb3soft.loja.repository.ProdutoRepository;
import br.com.bb3soft.loja.request.ProdutoRequest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class ProdutoServiceTest {

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    ProdutoMapper produtoMapper;

    @InjectMocks
    ProdutoService produtoService;

    private static final String PRODUTO_NAO_ENCONTRADO = "Produto com id %s não encontrado";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("deve salvar um produto com sucesso")
    void salvar_produto_com_sucesso() {
        var produtoRequest = ProdutoRequest.builder()
                .nome("Celular novo")
                .descricao("Celular novo na caixa")
                .valor(new BigDecimal("2000"))
                .quantidadeEstoque(20)
                .build();

        var produto = Produto.builder()
                .id(1l)
                .nome("Video game")
                .descricao("video game preto")
                .valor(new BigDecimal("5400"))
                .quantidadeEstoque(2)
                .build();

        var produtoDTO = new ProdutoDTO(1L, "mouse", "mouse novo", new BigDecimal("40"),
                15, LocalDateTime.now(), LocalDateTime.now());

        when(produtoMapper.produtoRequestToProduto(produtoRequest)).thenReturn(produto);
        when(produtoMapper.produtoTODTO(produto)).thenReturn(produtoDTO);

        var response = produtoService.salvar(produtoRequest);
        assertNotNull(response);
        assertEquals(ProdutoDTO.class, response.getClass());
        assertEquals("mouse", response.nome());
        assertEquals("mouse novo", response.descricao());
    }

    @Test
    @DisplayName("deve atualizar um produto com sucesso")
    void atualizar_produto_com_sucesso() {
        var produtoRequest = ProdutoRequest.builder()
                .nome("Celular novo")
                .descricao("Celular novo na caixa")
                .valor(new BigDecimal("2000"))
                .quantidadeEstoque(20)
                .build();

        var produto = Produto.builder()
                .id(1l)
                .nome("Video game")
                .descricao("video game preto")
                .valor(new BigDecimal("5400"))
                .quantidadeEstoque(2)
                .build();

        var produtoDTO = new ProdutoDTO(1L, "mouse", "mouse novo", new BigDecimal("40"),
                15, LocalDateTime.now(), LocalDateTime.now());

        when(produtoRepository.findByIdOptional(1L)).thenReturn(Optional.of(produto));
        when(produtoMapper.produtoTODTO(produto)).thenReturn(produtoDTO);

        var response = produtoService.atualizar(1L, produtoRequest);
        assertNotNull(response);
        assertEquals(ProdutoDTO.class, response.getClass());
        assertEquals("mouse", response.nome());
        assertEquals("mouse novo", response.descricao());

        verify(produtoRepository, times(1)).findByIdOptional(1L);
    }

    @Test
    @DisplayName("deve remover um produto com sucesso")
    void remover_produto_com_sucesso() {
        var produto = Produto.builder()
                .id(1l)
                .nome("Video game")
                .descricao("video game preto")
                .valor(new BigDecimal("5400"))
                .quantidadeEstoque(2)
                .build();

        when(produtoRepository.findByIdOptional(1L)).thenReturn(Optional.of(produto));
        produtoService.remover(1L);

        verify(produtoRepository, times(1)).findByIdOptional(1L);
    }

    @Test
    @DisplayName("deve buscar por id com sucesso")
    void buscar_por_id_produto_com_sucesso() {
        var produto = Produto.builder()
                .id(1l)
                .nome("Video game")
                .descricao("video game preto")
                .valor(new BigDecimal("5400"))
                .quantidadeEstoque(2)
                .build();

        var produtoDTO = new ProdutoDTO(1L, "mouse", "mouse novo", new BigDecimal("40"),
                15, LocalDateTime.now(), LocalDateTime.now());

        when(produtoRepository.findByIdOptional(1L)).thenReturn(Optional.of(produto));
        when(produtoMapper.produtoTODTO(produto)).thenReturn(produtoDTO);
        var response = produtoService.buscarPorId(1L);
        assertNotNull(response);
        assertEquals(ProdutoDTO.class, response.getClass());
        assertEquals("mouse", response.nome());
        assertEquals("mouse novo", response.descricao());
        verify(produtoRepository, times(1)).findByIdOptional(1L);
    }

    @Test
    @DisplayName("deve lançar uma exception quando atualizar um produto nao cadastrado no banco")
    void erro_ao_atualizar_produto() {
        var produtoRequest = ProdutoRequest.builder()
                .nome("Celular novo")
                .descricao("Celular novo na caixa")
                .valor(new BigDecimal("2000"))
                .quantidadeEstoque(20)
                .build();

        assertThrows(ProdutoNaoEncontradoExecption.class, () -> {
            produtoService.atualizar(1L, produtoRequest);
        });
    }

    @Test
    @DisplayName("deve lançar uma exception quando remover um produto nao cadastrado no banco")
    void erro_ao_remover_produto() {
        assertThrows(ProdutoNaoEncontradoExecption.class, () -> {
            produtoService.remover(1L);
        });
    }

    @Test
    @DisplayName("deve lançar uma exception quando buscar um produto nao cadastrado no banco")
    void erro_ao_buscar_produto() {
        assertThrows(ProdutoNaoEncontradoExecption.class, () -> {
            produtoService.buscarPorId(1L);
        });
    }
}