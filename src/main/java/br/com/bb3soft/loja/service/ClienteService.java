package br.com.bb3soft.loja.service;

import br.com.bb3soft.loja.dto.ClienteDTO;
import br.com.bb3soft.loja.enums.Situacao;
import br.com.bb3soft.loja.enums.Tipo;
import br.com.bb3soft.loja.exception.ClienteNaoEncontradoException;
import br.com.bb3soft.loja.mapper.ClienteMapper;
import br.com.bb3soft.loja.repository.ClienteRepository;
import br.com.bb3soft.loja.request.ClienteRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class ClienteService {

    private ClienteRepository clienteRepository;
    private ClienteMapper clienteMapper;

    private static final String CLIENTE_NAO_ENCONTADO = "Cliente com id : %s não encontrado";

    @Inject
    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteMapper = clienteMapper;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ClienteDTO salvar(ClienteRequest clienteRequest) {
        var cliente = clienteMapper.clienteRequestToCliente(clienteRequest);
        cliente.setSituacao(Situacao.ATIVO);
        cliente.setTipo(Tipo.PESSOA_FISICA);
        clienteRepository.persist(cliente);
        return clienteMapper.clienteTODTO(cliente);
    }

    @Transactional
    public ClienteDTO atualizar(Long clienteId, ClienteRequest clienteRequest) {
        var cliente = clienteRepository.findByIdOptional(clienteId)
                .orElseThrow(() -> new ClienteNaoEncontradoException(String.format(CLIENTE_NAO_ENCONTADO, clienteId)));

        cliente.setRg(clienteRequest.getRg());
        cliente.setCelular(clienteRequest.getCelular());
        cliente.setCpf(clienteRequest.getCpf());
        cliente.setEmail(clienteRequest.getEmail());
        cliente.setNome(clienteRequest.getNome());
        cliente.setTipo(clienteRequest.getTipo());
        cliente.setDataNascimento(clienteRequest.getDataNascimento());

        cliente.getEndereco().setUf(clienteRequest.getEndereco().getUf());
        cliente.getEndereco().setRua(clienteRequest.getEndereco().getRua());
        cliente.getEndereco().setNumero(clienteRequest.getEndereco().getNumero());
        cliente.getEndereco().setCidade(clienteRequest.getEndereco().getCidade());
        cliente.getEndereco().setBairro(clienteRequest.getEndereco().getBairro());
        cliente.getEndereco().setComplemento(clienteRequest.getEndereco().getComplemento());

        return clienteMapper.clienteTODTO(cliente);
    }

    @Transactional
    public void remover(Long clienteId) {
        var cliente = clienteRepository.findByIdOptional(clienteId)
                .orElseThrow(() -> new ClienteNaoEncontradoException(String.format(CLIENTE_NAO_ENCONTADO, clienteId)));

        clienteRepository.delete(cliente);
    }

    public ClienteDTO buscarPorId(Long clienteId) {
        return clienteRepository.findByIdOptional(clienteId)
                .map(clienteMapper::clienteTODTO)
                .orElseThrow(() -> new ClienteNaoEncontradoException(String.format(CLIENTE_NAO_ENCONTADO, clienteId)));
    }

    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::clienteTODTO)
                .sorted(Comparator.comparing(ClienteDTO::id))
                .toList();
    }

}
