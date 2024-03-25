package br.com.bb3soft.loja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pagamento_venda")
@Data
public class PagamentoVenda {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private BigDecimal valorPago;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PagamentoVenda{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", valorPago=" + valorPago +
                '}';
    }

}
