package br.com.mulero.miniautorizador.controller;

import br.com.mulero.miniautorizador.dto.CartaoDTO;
import br.com.mulero.miniautorizador.service.CartaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
@Tag(name = "operation.card", description = "operation.card.description")
@RequiredArgsConstructor
public class CartaoController {

    private final CartaoService cartaoService;

    @PostMapping
    @Operation(summary = "operation.card.create.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "operation.card.create.success"),
            @ApiResponse(responseCode = "401", description = "operation.card.create.unauthorized"),
            @ApiResponse(responseCode = "422", description = "operation.card.create.unprocessableEntity")
    })
    public ResponseEntity<CartaoDTO> criarCartao(@RequestBody CartaoDTO cartaoDTO) {
        return cartaoService.salvar(cartaoDTO);
    }

    @GetMapping("/{numeroCartao}")
    @Operation(summary = "operation.card.balance.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operation.card.balance.success"),
            @ApiResponse(responseCode = "401", description = "operation.card.balance.unauthorized"),
            @ApiResponse(responseCode = "404", description = "operation.card.balance.notFound")
    })
    public ResponseEntity<BigDecimal> obterSaldo(@PathVariable String numeroCartao) {
        return cartaoService.obterSaldo(numeroCartao);
    }

}
