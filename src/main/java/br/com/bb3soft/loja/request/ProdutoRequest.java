package br.com.bb3soft.loja.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProdutoRequest {

    @NotBlank(message = "O campo nome não pode ser em branco")
    private String nome;

    @NotBlank(message = "O campo descrição não pode ser em branco")
    private String descricao;

    @NotNull(message = "O campo valor não pode ser em branco")
    private BigDecimal valor;

    @NotNull(message = "O campo quantidade em estoque não pode ser em branco")
    private Integer quantidadeEstoque;

}
