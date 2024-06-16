package br.com.mulero.miniautorizador.infrastructure.exception;

public class CardNotFoundException extends TransactionException {

    public CardNotFoundException() {
        super("error.card.not.found");
    }
}
