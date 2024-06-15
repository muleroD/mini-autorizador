package br.com.mulero.miniautorizador.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransacaoDTO extends CartaoDTO {

    @NotNull(message = "validation.transaction.amount.notBlank")
    @Schema(description = "Valor da transação", example = "100.00")
    private BigDecimal valor;

}
