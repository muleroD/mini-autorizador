package br.com.mulero.miniautorizador.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartoes")
@Tag(name = "operation.card", description = "operation.card.description")
public class CartaoController {

    @PostMapping
    @Operation(summary = "operation.card.create.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "operation.card.create.success"),
            @ApiResponse(responseCode = "401", description = "operation.card.create.unauthorized"),
            @ApiResponse(responseCode = "422", description = "operation.card.create.unprocessableEntity")
    })
    public void criarCartao() {
    }

    @GetMapping("/{numeroCartao}")
    @Operation(summary = "operation.card.balance.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operation.card.balance.success"),
            @ApiResponse(responseCode = "401", description = "operation.card.balance.unauthorized"),
            @ApiResponse(responseCode = "404", description = "operation.card.balance.notFound")
    })
    public void obterSaldo(@PathVariable String numeroCartao) {
    }

}
