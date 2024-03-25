package br.com.bb3soft.loja.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoDTO(Long id,
                         String nome,
                         String descricao,
                         BigDecimal valor,
                         Integer quantidadeEstoque,
                         @JsonFormat(pattern = "dd/MM/yyyy")
                         LocalDateTime dataCadastro,
                         @JsonFormat(pattern = "dd/MM/yyyy")
                         LocalDateTime dataAtualizacao) {
}
