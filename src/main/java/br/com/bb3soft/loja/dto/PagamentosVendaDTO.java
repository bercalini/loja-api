package br.com.bb3soft.loja.dto;

import java.math.BigDecimal;

public record PagamentosVendaDTO(String nome,
                                 BigDecimal valorPago) {
}
