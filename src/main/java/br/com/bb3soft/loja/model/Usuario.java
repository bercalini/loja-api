package br.com.bb3soft.loja.model;

import br.com.bb3soft.loja.enums.Situacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String senha;
    private String email;

    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dataAtualizacao;

}
