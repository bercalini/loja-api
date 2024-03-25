package br.com.bb3soft.loja.service;

import br.com.bb3soft.loja.dto.ProdutoDTO;
import br.com.bb3soft.loja.exception.ProdutoNaoEncontradoExecption;
import br.com.bb3soft.loja.mapper.ProdutoMapper;
import br.com.bb3soft.loja.repository.ProdutoRepository;
import br.com.bb3soft.loja.request.ProdutoRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class ProdutoService {

    private ProdutoRepository produtoRepository;
    private ProdutoMapper produtoMapper;
    private static final String PRODUTO_NAO_ENCONTRADO = "Produto com id %s não encontrado";

    @Inject
    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    @Transactional
    public ProdutoDTO salvar(ProdutoRequest produtoRequest) {
        var produto = produtoMapper.produtoRequestToProduto(produtoRequest);
        produto.setValor(produtoRequest.getValor());
        produtoRepository.persist(produto);
        return produtoMapper.produtoTODTO(produto);
    }

    @Transactional
    public ProdutoDTO atualizar(Long produtoId, ProdutoRequest produtoRequest) {
        var produto = produtoRepository
                .findByIdOptional(produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoExecption(String.format(PRODUTO_NAO_ENCONTRADO, produtoId)));

        produto.setNome(produtoRequest.getNome());
        produto.setDescricao(produtoRequest.getDescricao());
        produto.setValor(produtoRequest.getValor());
        produto.setQuantidadeEstoque(produtoRequest.getQuantidadeEstoque());

        return produtoMapper.produtoTODTO(produto);
    }

    @Transactional
    public void remover(Long produtoId) {
        var produto = produtoRepository
                .findByIdOptional(produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoExecption(String.format(PRODUTO_NAO_ENCONTRADO, produtoId)));

        produtoRepository.delete(produto);
    }

    public List<ProdutoDTO> listar() {
        return produtoRepository.findAll()
                .stream()
                .map(produtoMapper::produtoTODTO)
                .sorted(Comparator.comparing(ProdutoDTO::id))
                .toList();
    }

    public ProdutoDTO buscarPorId(Long produtoId) {
        return produtoRepository.findByIdOptional(produtoId)
                .map(produtoMapper::produtoTODTO)
                .orElseThrow(() -> new ProdutoNaoEncontradoExecption(String.format(PRODUTO_NAO_ENCONTRADO, produtoId)));

    }

}
