package br.com.bb3soft.loja.dto;

import java.math.BigDecimal;

public record ItensVendaDTO(ProdutoVendaDTO produto,
                            Integer quantidadeProduto,
                            BigDecimal valorProduto) {
}
