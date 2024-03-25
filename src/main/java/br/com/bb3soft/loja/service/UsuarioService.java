package br.com.bb3soft.loja.service;

import br.com.bb3soft.loja.dto.UsuarioDTO;
import br.com.bb3soft.loja.enums.Situacao;
import br.com.bb3soft.loja.exception.UsuarioNaoEnconradoException;
import br.com.bb3soft.loja.mapper.UsuarioMapper;
import br.com.bb3soft.loja.repository.UsuarioRepository;
import br.com.bb3soft.loja.request.UsuarioRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private UsuarioMapper usuarioMapper;
    private static final String USUARIO_NAO_ENCONTRADO = "Usuario com id : %s não encontrado";

    @Inject
    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Transactional
    public UsuarioDTO salvar(UsuarioRequest usuarioRequest) {
        var usuario = usuarioMapper.usuarioRequestTOUsuario(usuarioRequest);
        usuario.setSituacao(Situacao.ATIVO);

        usuarioRepository.persist(usuario);
        return usuarioMapper.usuarioTOUsuarioDTO(usuario);
    }

    @Transactional
    public UsuarioDTO editar(Long usuarioId, UsuarioRequest usuarioRequest) {
        var usuario = usuarioRepository.findByIdOptional(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEnconradoException(String.format(USUARIO_NAO_ENCONTRADO, usuarioId)));


        usuario.setNome(usuarioRequest.getNome());
        usuario.setSenha(usuarioRequest.getSenha());
        usuario.setEmail(usuarioRequest.getEmail());

        return usuarioMapper.usuarioTOUsuarioDTO(usuario);
    }

    @Transactional
    public void excluir(Long usuarioId) {
        var usuario = usuarioRepository.findByIdOptional(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEnconradoException(String.format(USUARIO_NAO_ENCONTRADO, usuarioId)));

        usuarioRepository.delete(usuario);
    }

    @Transactional
    public void inativar(Long usuarioId) {
        var usuario = usuarioRepository.findByIdOptional(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEnconradoException(String.format(USUARIO_NAO_ENCONTRADO, usuarioId)));

        usuario.setSituacao(Situacao.INATIVO);
    }

    @Transactional
    public void ativar(Long usuarioId) {
        var usuario = usuarioRepository.findByIdOptional(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEnconradoException(String.format(USUARIO_NAO_ENCONTRADO, usuarioId)));

        usuario.setSituacao(Situacao.ATIVO);
    }

    public UsuarioDTO buscarPorId(Long usuarioId) {
        return usuarioRepository.findByIdOptional(usuarioId)
                .map(usuarioMapper::usuarioTOUsuarioDTO)
                .orElseThrow(() -> new UsuarioNaoEnconradoException(String.format(USUARIO_NAO_ENCONTRADO, usuarioId)));
    }

    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::usuarioTOUsuarioDTO)
                .sorted(Comparator.comparing(UsuarioDTO::id))
                .toList();
    }

}
