package br.com.bb3soft.loja.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PagamentosVendaRequest {

    private Long pagamentoId;
    private BigDecimal valorPago;

}
