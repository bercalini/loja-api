package br.com.bb3soft.loja.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;

    private BigDecimal valor;
    private Integer quantidadeEstoque;

    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dataCadastro;

    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dataAtualizacao;
}
