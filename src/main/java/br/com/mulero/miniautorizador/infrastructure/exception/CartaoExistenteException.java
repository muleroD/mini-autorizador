package br.com.mulero.miniautorizador.infrastructure.exception;

import br.com.mulero.miniautorizador.dto.CartaoDTO;
import lombok.Getter;

@Getter
public class CartaoExistenteException extends RuntimeException {

    private final transient CartaoDTO cartaoDTO;

    public CartaoExistenteException(CartaoDTO cartaoDTO) {
        this.cartaoDTO = cartaoDTO;
    }

}
