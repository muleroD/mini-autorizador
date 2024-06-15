package br.com.mulero.miniautorizador.controller;

import br.com.mulero.miniautorizador.dto.TransacaoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
@Tag(name = "operation.transaction", description = "operation.transaction.description")
@RequiredArgsConstructor
public class TransacaoController {

    @PostMapping
    @Operation(summary = "operation.transaction.create.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "operation.transaction.create.success",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "422", description = "operation.common.unprocessableEntity",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "operation.common.unauthorized",
                    content = @Content(schema = @Schema()))
    })
    public void realizarTransacao(@Valid @RequestBody TransacaoDTO transacaoDTO) {
        throw new UnsupportedOperationException("Operação não suportada");
    }
}
