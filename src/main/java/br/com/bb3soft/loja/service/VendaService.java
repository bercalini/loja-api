package br.com.bb3soft.loja.service;

import br.com.bb3soft.loja.dto.*;
import br.com.bb3soft.loja.exception.ClienteNaoEncontradoException;
import br.com.bb3soft.loja.exception.FormaPagamentoNaoEncontradoException;
import br.com.bb3soft.loja.exception.ProdutoNaoEncontradoExecption;
import br.com.bb3soft.loja.model.*;
import br.com.bb3soft.loja.repository.ClienteRepository;
import br.com.bb3soft.loja.repository.FormaPagamentoRepository;
import br.com.bb3soft.loja.repository.ProdutoRepository;
import br.com.bb3soft.loja.repository.VendaRepository;
import br.com.bb3soft.loja.request.VendaRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class VendaService {

    private VendaRepository vendaRepository;
    private ClienteRepository clienteRepository;
    private ProdutoRepository produtoRepository;
    private FormaPagamentoRepository formaPagamentoRepository;

    private static final String CLIENTE_NAO_ENCONTADO = "Cliente com id : %s não encontrado";
    private static final String PRODUTO_NAO_ENCONTRADO = "Produto com id %s não encontrado";

    @Inject
    public VendaService(VendaRepository vendaRepository, ClienteRepository clienteRepository,
        ProdutoRepository produtoRepository, FormaPagamentoRepository formaPagamentoRepository) {
        this.vendaRepository = vendaRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    @Transactional
    public void realizarVenda(VendaRequest vendaRequest) {
        Venda venda = new Venda();
        Set<ItemVenda> itensVenda = new HashSet<>();
        Set<PagamentoVenda> pagamentos = new HashSet<>();

        var cliente = clienteRepository.findByIdOptional(vendaRequest.getCliente().getId())
                .orElseThrow(() -> new ClienteNaoEncontradoException
                        (String.format(CLIENTE_NAO_ENCONTADO, vendaRequest.getCliente().getId())));

        BigDecimal valorTotal = vendaRequest.getItensVenda().stream()
                .map(item -> {
                    Produto produto = produtoRepository.findByIdOptional(item.getProdutoId())
                            .orElseThrow(() -> new ProdutoNaoEncontradoExecption(
                                    String.format("Produto com ID %d não encontrado", item.getProdutoId())));
                    BigDecimal valorItem = produto.getValor().multiply(BigDecimal.valueOf(item.getQuantidade()));

                    ItemVenda novoItemVenda = new ItemVenda();
                    novoItemVenda.setProduto(produto);
                    novoItemVenda.setQuantidadeProduto(item.getQuantidade());
                    novoItemVenda.setValorProduto(produto.getValor());
                    novoItemVenda.setVenda(venda);
                    itensVenda.add(novoItemVenda);
                    return valorItem;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        vendaRequest.getPagamentosVenda().forEach(p -> {
            FormaPagamento formaPagamento = formaPagamentoRepository.findByIdOptional
                    (p.getPagamentoId()).orElseThrow(() -> new FormaPagamentoNaoEncontradoException(
                    "Forma de pagamento com o id: " + p.getPagamentoId() + " não encontrado"));

            PagamentoVenda pagamentoVenda = new PagamentoVenda();
            pagamentoVenda.setNome(formaPagamento.getNome());
            pagamentoVenda.setValorPago(p.getValorPago());
            pagamentoVenda.setVenda(venda);
            pagamentos.add(pagamentoVenda);

        });

        venda.setValorTotal(valorTotal);
        venda.setPagamentosVenda(pagamentos);
        venda.setItensVenda(itensVenda);
        venda.setCliente(cliente);
        vendaRepository.persist(venda);

    }

    public List<VendaDTO> listarVendas() {
        List<VendaDTO> vendasDTOS = new ArrayList<>();

        vendaRepository.findAllWithItensAndPagamentos().forEach(v -> {
            Set<ItensVendaDTO> itensVendaDTOS = new HashSet<>();
            Set<PagamentosVendaDTO> pagamentosVendaDTOS = new HashSet<>();

            v.getItensVenda().forEach(itens -> {
                ProdutoVendaDTO produtoDTO = new ProdutoVendaDTO(itens.getProduto().getId(),
                        itens.getProduto().getNome(), itens.getProduto().getValor());

                ItensVendaDTO itensVendaDTO = new ItensVendaDTO(produtoDTO, itens.getQuantidadeProduto(),
                        itens.getValorProduto());

                itensVendaDTOS.add(itensVendaDTO);
            });

            v.getPagamentosVenda().forEach(pagamento -> {
                PagamentosVendaDTO pagamentosVendaDTO = new PagamentosVendaDTO(pagamento.getNome(),
                        pagamento.getValorPago());

                pagamentosVendaDTOS.add(pagamentosVendaDTO);
            });

            ClienteVendaDTO clienteVendaDTO = new ClienteVendaDTO(v.getCliente().getId(), v.getCliente().getNome(),
                    v.getCliente().getEmail(), v.getCliente().getCelular());

            VendaDTO vendaDTO = new VendaDTO(v.getId(), v.getDataVenda(), v.getValorTotal(),
                    itensVendaDTOS, pagamentosVendaDTOS, clienteVendaDTO);

            vendasDTOS.add(vendaDTO);
        });

        return vendasDTOS;
    }


    public List<Venda> listarVendasTeste() {
        return vendaRepository.findAllWithItensAndPagamentos().stream().toList();
    }
}
