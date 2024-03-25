package br.com.bb3soft.loja.repository;

import br.com.bb3soft.loja.model.Venda;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;

import java.util.List;

@ApplicationScoped
public class VendaRepository implements PanacheRepository<Venda> {

    public List<Venda> findAllWithItensAndPagamentos() {
        String jpql = "SELECT DISTINCT v FROM Venda v INNER JOIN FETCH v.itensVenda INNER JOIN FETCH " +
                "v.pagamentosVenda ORDER BY v.id DESC";
        Query query = getEntityManager().createQuery(jpql, Venda.class);
        return query.getResultList();
    }

}
