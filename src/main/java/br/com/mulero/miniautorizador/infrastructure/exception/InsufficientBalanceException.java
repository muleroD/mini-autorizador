package br.com.mulero.miniautorizador.infrastructure.exception;

public class InsufficientBalanceException extends TransactionException {

    public InsufficientBalanceException() {
        super("error.insufficient.balance");
    }
}
