package br.com.mulero.miniautorizador.infrastructure.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TransactionException extends RuntimeException {

    public TransactionException(String message) {
        super(message);
    }
}
