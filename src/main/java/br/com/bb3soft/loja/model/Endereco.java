package br.com.bb3soft.loja.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Builder
@Data
public class Endereco {

    private String complemento;
    private String numero;
    private String bairro;
    private String cidade;
    private String rua;
    private String uf;

}
