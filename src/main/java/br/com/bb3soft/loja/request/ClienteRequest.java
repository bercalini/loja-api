package br.com.bb3soft.loja.request;

import br.com.bb3soft.loja.enums.Tipo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ClienteRequest {

    @NotBlank(message = "O campo nome não pode ser em branco")
    private String nome;

    private Tipo tipo;

    @NotBlank(message = "O campo cpf não pode ser em branco")
    @CPF(message = "Passe um CPF valido")
    private String cpf;

    @NotBlank(message = "O campo rg não pode ser em branco")
    private String rg;

    @NotBlank(message = "O campo nome não pode ser em branco")
    private String celular;

    @NotBlank(message = "O campo nome não pode ser em branco")
    @Email(message = "Digite um email valido")
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Valid
    @NotNull(message = "O campo endereco não pode ser em branco")
    private EnderecoRequest endereco;

}
