package br.com.bb3soft.loja.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record VendaDTO(Long id,
                       @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
                       LocalDateTime dataVenda,
                       BigDecimal valorTotal,
                       Set<ItensVendaDTO> itensVenda,
                       Set<PagamentosVendaDTO> pagamentosVenda,
                       ClienteVendaDTO cliente) {
}
