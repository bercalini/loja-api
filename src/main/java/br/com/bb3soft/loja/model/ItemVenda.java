package br.com.bb3soft.loja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "item_venda")
@Data
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private Integer quantidadeProduto;
    private BigDecimal valorProduto;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toString() {
        return "ItemVenda{" +
                "id=" + id +
                ", produto=" + produto +
                ", quantidadeProduto=" + quantidadeProduto +
                ", valorProduto=" + valorProduto +
                '}';
    }

}
