package br.com.mulero.miniautorizador.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CartaoDTO(
        @NotNull
        @NotEmpty
        @Size(min = 16, max = 16)
        String numeroCartao,

        @NotNull
        @NotEmpty
        @Size(min = 4, max = 4)
        String senha
) {
}
