package br.com.mulero.miniautorizador.infrastructure.exception;

import br.com.mulero.miniautorizador.dto.CardDTO;
import lombok.Getter;

@Getter
public class CardAlreadyExistsException extends IllegalArgumentException {

    private final transient CardDTO cardDTO;

    public CardAlreadyExistsException(CardDTO cardDTO) {
        this.cardDTO = cardDTO;
    }

}
