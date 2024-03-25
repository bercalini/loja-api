package br.com.bb3soft.loja.dto;

public record EnderecoDTO(String complemento,
                          String numero,
                          String bairro,
                          String cidade,
                          String rua,
                          String uf) {
}
