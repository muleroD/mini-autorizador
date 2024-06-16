package br.com.mulero.miniautorizador.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

    @NotBlank(message = "validation.card.number.notBlank")
    @Pattern(regexp = "\\d{16}", message = "validation.card.number.pattern")
    @Schema(description = "Número do cartão", example = "1234567890123456")
    @JsonProperty("numeroCartao")
    String cardNumber;

    @NotBlank(message = "validation.card.password.notBlank")
    @Pattern(regexp = "\\d{4}", message = "validation.card.password.pattern")
    @Schema(description = "Senha do cartão", example = "1234")
    @JsonProperty("senha")
    String password;

}
