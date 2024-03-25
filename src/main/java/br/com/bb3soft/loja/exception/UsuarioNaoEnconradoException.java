package br.com.bb3soft.loja.exception;

public class UsuarioNaoEnconradoException extends RuntimeException{

    public UsuarioNaoEnconradoException(String mensagem) {
        super(mensagem);
    }

}
