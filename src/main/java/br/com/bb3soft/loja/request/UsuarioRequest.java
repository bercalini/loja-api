package br.com.bb3soft.loja.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UsuarioRequest {

    @NotBlank(message = "O campo nome não pode ser em branco")
    private String nome;

    @NotBlank(message = "O campo senha não pode ser em branco")
    private String senha;

    @NotBlank(message = "O campo email não pode ser em branco")
    @Email(message = "Passe um email valido")
    private String email;

}
