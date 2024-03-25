package br.com.bb3soft.loja.dto;

import br.com.bb3soft.loja.enums.Situacao;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record UsuarioDTO(Long id,
                         String nome,
                         String email,
                         String senha,
                         Situacao situacao,
                         @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
                         LocalDateTime dataCadastro,
                         @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
                         LocalDateTime dataAtualizacao
                         ) {
}
