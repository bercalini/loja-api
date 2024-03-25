package br.com.bb3soft.loja.resource;

import br.com.bb3soft.loja.dto.ClienteDTO;
import br.com.bb3soft.loja.dto.EnderecoDTO;
import br.com.bb3soft.loja.enums.Situacao;
import br.com.bb3soft.loja.enums.Tipo;
import br.com.bb3soft.loja.model.Cliente;
import br.com.bb3soft.loja.model.Endereco;
import br.com.bb3soft.loja.request.ClienteRequest;
import br.com.bb3soft.loja.request.EnderecoRequest;
import br.com.bb3soft.loja.service.ClienteService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpStatus;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
class ClienteResourceTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteResource clienteResource;

    private Cliente cliente;
    private ClienteRequest clienteRequest;
    private ClienteDTO clienteDTO;

    @BeforeEach
    public void setup() {
        starterCliente();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve fazer a consulta no end poind do buscar cliente")
    void deve_fazer_uma_consulta_e_verificar_o_resource() {
        lenient().when(clienteService.buscarPorId(anyLong())).thenReturn(clienteDTO);

        var result = clienteResource.buscarPorId(cliente.getId());

        assertEquals(ClienteDTO.class, result.getClass());
        assertEquals("Murilo", result.nome());
        assertEquals("teste@teste.com", result.email());
    }

    @Test
    @DisplayName("Deve salvar um cliente")
    void salvar_cliente() {
        when(clienteService.salvar(clienteRequest)).thenReturn(clienteDTO);

        Response response = clienteResource.salvar(clienteRequest);

        assertEquals(HttpStatus.SC_CREATED, response.getStatus());
    }

    @Test
    @DisplayName("Deve atualziar um cliente")
    void atualizar_cliente() {
        when(clienteService.atualizar(cliente.getId(), clienteRequest)).thenReturn(clienteDTO);

        ClienteDTO result = clienteResource.atualizar(cliente.getId(), clienteRequest);
        assertEquals(ClienteDTO.class, result.getClass());
        assertEquals("Murilo", result.nome());
        assertEquals("São paulo", result.endereco().uf());
        assertEquals("Marilandia do sul", result.endereco().cidade());
    }

    @Test
    @DisplayName("Remover cliente com sucesso")
    void remover_cliente_com_suscesso() {
        doNothing().when(clienteService).remover(cliente.getId());

        Response response = clienteResource.remover(cliente.getId());

        verify(clienteService).remover(cliente.getId());
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    @DisplayName("Listar clientes")
    void listar_clientes() {
        when(clienteService.listarTodos()).thenReturn(List.of(clienteDTO));

        List<ClienteDTO> resultList = clienteResource.listar();

        assertNotNull(resultList);
        assertEquals("Murilo", resultList.get(0).nome());
        assertEquals("São paulo", resultList.get(0).endereco().uf());
        assertEquals("Marilandia do sul", resultList.get(0).endereco().cidade());
        assertEquals(1, resultList.size());
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