package br.com.mulero.miniautorizador.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartaoDTO {

    @NotBlank(message = "validation.card.number.notBlank")
    @Size(min = 16, max = 16, message = "validation.card.number.size")
    @Pattern(regexp = "\\d{16}", message = "validation.card.number.pattern")
    @Schema(description = "Número do cartão", example = "1234567890123456")
    String numeroCartao;

    @NotBlank(message = "validation.card.password.notBlank")
    @Size(min = 4, max = 4, message = "validation.card.password.size")
    @Pattern(regexp = "\\d{4}", message = "validation.card.password.pattern")
    @Schema(description = "Senha do cartão", example = "1234")
    String senha;

}
