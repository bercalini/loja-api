package br.com.bb3soft.loja.repository;

import br.com.bb3soft.loja.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {
}
