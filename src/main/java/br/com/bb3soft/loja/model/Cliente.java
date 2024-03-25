package br.com.bb3soft.loja.model;

import br.com.bb3soft.loja.enums.Situacao;
import br.com.bb3soft.loja.enums.Tipo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Tipo tipo;
    private Situacao situacao;
    private String cpf;
    private String rg;
    private String celular;
    private String email;
    private LocalDate dataNascimento;

    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dataAtualizacao;

    @Embedded
    private Endereco endereco;
}
