package br.com.bb3soft.loja.service;

import br.com.bb3soft.loja.dto.ClienteDTO;
import br.com.bb3soft.loja.dto.EnderecoDTO;
import br.com.bb3soft.loja.enums.Situacao;
import br.com.bb3soft.loja.enums.Tipo;
import br.com.bb3soft.loja.exception.ClienteNaoEncontradoException;
import br.com.bb3soft.loja.mapper.ClienteMapper;
import br.com.bb3soft.loja.model.Cliente;
import br.com.bb3soft.loja.model.Endereco;
import br.com.bb3soft.loja.repository.ClienteRepository;
import br.com.bb3soft.loja.request.ClienteRequest;
import br.com.bb3soft.loja.request.EnderecoRequest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    ClienteMapper clienteMapper;

    @InjectMocks
    ClienteService clienteService;

    private Cliente cliente;
    private ClienteRequest clienteRequest;
    private ClienteDTO clienteDTO;

    private static final String CLIENTE_NAO_ENCONTRADO = "Cliente com id : %s não encontrado";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        starterCliente();
    }

    @Test
    @DisplayName("buscar cliente por id")
    void buscar_cliente_pelo_id() {
        when(clienteRepository.findByIdOptional(anyLong())).thenReturn(Optional.of(cliente));
        when(clienteMapper.clienteTODTO(cliente)).thenReturn(clienteDTO);

        ClienteDTO response = clienteService.buscarPorId(cliente.getId());

        assertNotNull(response);
        assertEquals(ClienteDTO.class, response.getClass());
        assertEquals("Murilo", response.nome());
        assertEquals(1L, response.id());
        assertEquals("10.07.10.02", response.rg());
    }

    @Test
    @DisplayName("Quando buscar por id, me retorne um objeto não encontrado")
    void cliente_nao_encontrado() {
        when(clienteRepository.findByIdOptional(anyLong()))
                .thenThrow(new ClienteNaoEncontradoException(String.format(CLIENTE_NAO_ENCONTRADO, cliente.getId())));

        ClienteNaoEncontradoException ex = assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteService.buscarPorId(cliente.getId());
        });

        assertEquals(String.format(CLIENTE_NAO_ENCONTRADO, cliente.getId()), ex.getMessage());
    }

    @Test
    @DisplayName("salvar um usuario com sucesso")
    void salvar_usuario_com_sucesso() {
        when(clienteMapper.clienteRequestToCliente(clienteRequest)).thenReturn(cliente);
        when(clienteMapper.clienteTODTO(cliente)).thenReturn(clienteDTO);

        var result = clienteService.salvar(clienteRequest);

        assertNotNull(result);
        assertEquals(ClienteDTO.class, result.getClass());
        assertEquals("Murilo", result.nome());
        assertEquals("São paulo", result.endereco().uf());
        assertEquals("Marilandia do sul", result.endereco().cidade());
    }

    @Test
    @DisplayName("atualizar usuario")
    void atualizar_usuario() {
        lenient().when(clienteMapper.clienteRequestToCliente(clienteRequest)).thenReturn(cliente);
        lenient().when(clienteMapper.clienteTODTO(cliente)).thenReturn(clienteDTO);
        lenient().when(clienteRepository.findByIdOptional(anyLong())).thenReturn(Optional.of(cliente));

        var result = clienteService.atualizar(cliente.getId(), clienteRequest);

        assertNotNull(result);
        assertEquals(ClienteDTO.class, result.getClass());
        assertEquals("Murilo", result.nome());
        assertEquals("São paulo", result.endereco().uf());
        assertEquals("Marilandia do sul", result.endereco().cidade());
    }

    @Test
    @DisplayName("erro ao tentar atualizar sem id")
    void erro_ao_tentar_atualizat_usuario_sem_id() {
        ClienteNaoEncontradoException ex = assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteService.atualizar(cliente.getId(), clienteRequest);
        });

        assertEquals(String.format(CLIENTE_NAO_ENCONTRADO, cliente.getId()), ex.getMessage());
    }

    @Test
    @DisplayName("remover cliente")
    void teste_excluir_cliente() {
        when(clienteRepository.findByIdOptional(cliente.getId())).thenReturn(Optional.of(cliente));

        clienteService.remover(cliente.getId());
        verify(clienteRepository, times(1)).delete(cliente);
    }

    @Test
    @DisplayName("remover um cliente sem id")
    void remover_cliente_sem_id() {
        assertThrows(ClienteNaoEncontradoException.class, () -> {
           clienteService.remover(cliente.getId());
        });
    }

    private void starterCliente() {
        cliente = Cliente.builder().cpf("100.291.659-38").email("teste@teste.com").rg("10.07.01").id(1L)
                .celular("43 998123456").nome("Alisson").tipo(Tipo.PESSOA_FISICA).dataCadastro(LocalDateTime.now())
                .dataNascimento(LocalDate.now()).dataAtualizacao(LocalDateTime.now()).situacao(Situacao.ATIVO)
                .endereco(Endereco.builder().rua("Rua 15").uf("Parana").bairro("Leão do norte")
                        .complemento("Perto da barbearia").numero("15").cidade("Marilandia do sul").build()).build();

        clienteRequest = ClienteRequest.builder().cpf("100.291.659-38").email("teste@teste.com").rg("10.07.01")
                .celular("43 998123456").nome("Nayla").tipo(Tipo.PESSOA_FISICA)
                .dataNascimento(LocalDate.now()).endereco(EnderecoRequest.builder().rua("Rua 15").uf("Parana")
                        .bairro("Leão do norte").complemento("Perto da barbearia").numero("15")
                        .cidade("Marilandia do sul").build()).build();

        clienteDTO = new ClienteDTO(1L, "Murilo", Tipo.PESSOA_FISICA,
                Situacao.ATIVO, "100.291.659-38", "10.07.10.02", "43 998124567",
                "teste@teste.com", LocalDate.now(), LocalDateTime.now(), LocalDateTime.now(),
                new EnderecoDTO("Perto da barbearia", "15", "Leão do norte",
                        "Marilandia do sul", "br 376", "São paulo"));
    }


}