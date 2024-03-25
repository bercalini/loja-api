package br.com.bb3soft.loja.exception;

public class FormaPagamentoNaoEncontradoException extends RuntimeException {

    public FormaPagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

}
