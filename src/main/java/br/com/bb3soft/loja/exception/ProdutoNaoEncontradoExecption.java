package br.com.bb3soft.loja.exception;

public class ProdutoNaoEncontradoExecption extends RuntimeException{

    public ProdutoNaoEncontradoExecption(String mensagem) {
        super(mensagem);
    }

}
