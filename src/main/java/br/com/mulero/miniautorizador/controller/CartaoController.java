package br.com.mulero.miniautorizador.controller;

import br.com.mulero.miniautorizador.dto.CartaoDTO;
import br.com.mulero.miniautorizador.service.CartaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
            @ApiResponse(responseCode = "201", description = "operation.card.create.success",
                    content = @Content(schema = @Schema(implementation = CartaoDTO.class))),
            @ApiResponse(responseCode = "422", description = "operation.common.unprocessableEntity",
                    content = @Content(schema = @Schema(implementation = CartaoDTO.class))),
            @ApiResponse(responseCode = "401", description = "operation.common.unauthorized",
                    content = @Content(schema = @Schema()))
    })
    public ResponseEntity<CartaoDTO> criarCartao(@Valid @RequestBody CartaoDTO cartaoDTO) {
        return cartaoService.salvar(cartaoDTO);
    }

    @GetMapping("/{numeroCartao}")
    @Operation(summary = "operation.card.balance.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operation.card.balance.success",
                    content = @Content(schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "404", description = "operation.common.notFound",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "operation.common.unauthorized",
                    content = @Content(schema = @Schema()))
    })
    public ResponseEntity<BigDecimal> obterSaldo(@PathVariable String numeroCartao) {
        return cartaoService.obterSaldo(numeroCartao);
    }

}
