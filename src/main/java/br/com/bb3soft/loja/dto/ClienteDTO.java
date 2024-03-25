package br.com.bb3soft.loja.dto;

import br.com.bb3soft.loja.enums.Situacao;
import br.com.bb3soft.loja.enums.Tipo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClienteDTO(Long id,
                         String nome,
                         Tipo tipo,
                         Situacao situacao,
                         String cpf,
                         String rg,
                         String celular,
                         String email,
                         @JsonFormat(pattern = "dd/MM/yyyy")
                         LocalDate dataNascimento,
                         @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
                         LocalDateTime dataCadastro,
                         @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
                         LocalDateTime dataAtualizacao,
                         EnderecoDTO endereco) {
}
