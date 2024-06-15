package br.com.mulero.miniautorizador.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CartaoDTO(
        @NotBlank(message = "O número do cartão é obrigatório")
        @Size(min = 16, max = 16, message = "O número do cartão deve ter 16 dígitos")
        @Schema(description = "Número do cartão", example = "1234567890123456")
        String numeroCartao,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 4, max = 4, message = "A senha deve ter 4 dígitos")
        @Schema(description = "Senha do cartão", example = "1234")
        String senha
) {
}
