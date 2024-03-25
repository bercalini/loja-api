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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
@Data
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dataVenda;

    private BigDecimal valorTotal;


    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private Set<ItemVenda> itensVenda = new HashSet<>();

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private Set<PagamentoVenda> pagamentosVenda = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Venda{id=").append(id);
        sb.append(", dataVenda=").append(dataVenda);
        sb.append(", valorTotal=").append(valorTotal);
        sb.append(", cliente=").append(cliente);
        sb.append(", itensVenda=[");
        for (ItemVenda item : itensVenda) {
            sb.append(item.getId()).append(", ");
        }
        sb.append("]");
        sb.append(", pagamentosVenda=[");
        for (PagamentoVenda pagamento : pagamentosVenda) {
            sb.append(pagamento.getId()).append(", ");
        }
        sb.append("]");
        sb.append('}');
        return sb.toString();
    }



}
