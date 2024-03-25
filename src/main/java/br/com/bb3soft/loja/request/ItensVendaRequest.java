package br.com.bb3soft.loja.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItensVendaRequest {

    private Long produtoId;
    private Integer quantidade;

}
