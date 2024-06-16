package br.com.mulero.miniautorizador.controller;

import br.com.mulero.miniautorizador.dto.TransactionDTO;
import br.com.mulero.miniautorizador.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacoes")
@Tag(name = "operation.transaction", description = "operation.transaction.description")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @Operation(summary = "operation.transaction.withdraw.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "operation.transaction.create.success",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "422", description = "operation.common.unprocessableEntity",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "operation.common.unauthorized",
                    content = @Content(schema = @Schema()))
    })
    public ResponseEntity<Object> withdraw(@Valid @RequestBody TransactionDTO transactionDTO) {
        transactionService.withdraw(transactionDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deposit/{cardNumber}")
    @Operation(summary = "operation.transaction.deposit.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "operation.transaction.create.success",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "422", description = "operation.common.unprocessableEntity",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "operation.common.unauthorized",
                    content = @Content(schema = @Schema()))
    })
    public ResponseEntity<Object> deposit(@Valid @RequestBody TransactionDTO transactionDTO, @PathVariable String cardNumber) {
        transactionService.deposit(transactionDTO, cardNumber);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfer/{cardNumber}")
    @Operation(summary = "operation.transaction.transfer.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "operation.transaction.create.success",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "422", description = "operation.common.unprocessableEntity",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "operation.common.unauthorized",
                    content = @Content(schema = @Schema()))
    })
    public ResponseEntity<Object> transfer(@Valid @RequestBody TransactionDTO transactionDTO, @PathVariable String cardNumber) {
        transactionService.transfer(transactionDTO, cardNumber);
        return ResponseEntity.ok().build();
    }
}
