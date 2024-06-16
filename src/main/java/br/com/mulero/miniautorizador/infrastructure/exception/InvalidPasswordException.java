package br.com.mulero.miniautorizador.infrastructure.exception;

public class InvalidPasswordException extends TransactionException {

    public InvalidPasswordException() {
        super("error.invalid.card.password");
    }
}
