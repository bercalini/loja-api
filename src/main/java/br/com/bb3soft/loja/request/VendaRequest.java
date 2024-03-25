package br.com.bb3soft.loja.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendaRequest {

    private ClienteVendaRequest cliente;

    private Set<ItensVendaRequest> itensVenda;
    private Set<PagamentosVendaRequest> pagamentosVenda;
}
