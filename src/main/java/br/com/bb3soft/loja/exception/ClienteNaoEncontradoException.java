package br.com.bb3soft.loja.exception;

public class ClienteNaoEncontradoException extends RuntimeException{

    public ClienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

}
