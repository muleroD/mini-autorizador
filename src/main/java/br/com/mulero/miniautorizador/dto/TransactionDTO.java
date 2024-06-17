package br.com.mulero.miniautorizador.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransactionDTO extends CardDTO {

    @NotNull(message = "validation.transaction.amount.notBlank")
    @Schema(description = "Valor da transação", example = "100.00")
    @JsonProperty("valor")
    private BigDecimal amount;

    public TransactionDTO(CardDTO cardDTO) {
        super(cardDTO.getCardNumber(), cardDTO.getPassword());
    }

    public TransactionDTO(CardDTO cardDTO, BigDecimal amount) {
        super(cardDTO.getCardNumber(), cardDTO.getPassword());
        this.amount = amount;
    }
}
