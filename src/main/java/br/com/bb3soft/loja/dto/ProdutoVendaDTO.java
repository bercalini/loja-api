package br.com.bb3soft.loja.dto;

import java.math.BigDecimal;

public record ProdutoVendaDTO(Long id,
                              String nome,
                              BigDecimal valorProduto) {
}
