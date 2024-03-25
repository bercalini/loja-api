package br.com.bb3soft.loja.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EnderecoRequest {

    private String complemento;
    private String numero;

    @NotBlank(message = "O campo bairro não pode ser em branco")
    private String bairro;

    @NotBlank(message = "O campo bairro não pode ser em branco")
    private String cidade;

    @NotBlank(message = "O campo bairro não pode ser em branco")
    private String rua;

    @NotBlank(message = "O campo bairro não pode ser em branco")
    private String uf;

}
