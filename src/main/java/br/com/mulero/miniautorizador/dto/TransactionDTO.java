package br.com.mulero.miniautorizador.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransactionDTO extends CardDTO {

    @NotNull(message = "validation.transaction.amount.notBlank")
    @Schema(description = "Valor da transação", example = "100.00")
    @JsonProperty("valor")
    private BigDecimal amount;

}
