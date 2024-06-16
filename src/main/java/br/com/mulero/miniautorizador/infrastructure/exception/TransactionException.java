package br.com.mulero.miniautorizador.infrastructure.exception;

import lombok.NoArgsConstructor;

import static br.com.mulero.miniautorizador.infrastructure.config.I18nConfig.RESOURCE_BUNDLE;

@NoArgsConstructor
public class TransactionException extends RuntimeException {

    public TransactionException(String message) {
        super(RESOURCE_BUNDLE.getString(message));
    }
}
