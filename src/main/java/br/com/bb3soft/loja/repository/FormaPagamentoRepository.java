package br.com.bb3soft.loja.repository;

import br.com.bb3soft.loja.model.FormaPagamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FormaPagamentoRepository implements PanacheRepository<FormaPagamento> {

}
