package br.com.bb3soft.loja.service;

import br.com.bb3soft.loja.exception.FormaPagamentoNaoEncontradoException;
import br.com.bb3soft.loja.model.FormaPagamento;
import br.com.bb3soft.loja.repository.FormaPagamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class FormaPagamentoService {

    private FormaPagamentoRepository formaPagamentoRepository;

    @Inject
    public FormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    @Transactional
    public void salvar(FormaPagamento formaPagamento) {
        formaPagamentoRepository.persist(formaPagamento);
    }

    public List<FormaPagamento> listar() {
       return formaPagamentoRepository.findAll()
                .stream()
                .toList();
    }

    public FormaPagamento buscarPorId(Long id) {
        return formaPagamentoRepository.findByIdOptional(id)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradoException(
                        "Forma de pagamento com o id: " + id + " não encontrado"));
    }

}
