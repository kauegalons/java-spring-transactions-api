package com.kauegalons.transactions.docs;

import com.kauegalons.transactions.dto.TransacaoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Transactions", description = "Create and delete transactions stored in memory")
public interface TransacaoControllerDoc {

    @Operation(
            summary = "Create a transaction",
            description = """
                    Validates the transaction and stores it in memory. Validation rules:
                    - `valor` and `dataHora` are required;
                    - `valor` must be greater than or equal to 0;
                    - `dataHora` must not be in the future.

                    Responses have no body.""",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Transaction to be created. `dataHora` must follow ISO 8601 with offset.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TransacaoDTO.class),
                            examples = @ExampleObject(
                                    name = "Valid transaction",
                                    value = """
                                            {
                                              "valor": 123.45,
                                              "dataHora": "2020-08-07T12:34:56.789-03:00"
                                            }"""
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transaction accepted and stored.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Malformed or unreadable request body.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Transaction failed one or more validation rules: missing field, negative value or future date.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error while processing the request.", content = @Content)
    })
    ResponseEntity<Void> criarTransacao(TransacaoDTO transacaoDTO);

    @Operation(
            summary = "Delete all transactions",
            description = "Removes every transaction stored in memory. The response has no body."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All transactions deleted.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error while processing the request.", content = @Content)
    })
    ResponseEntity<Void> deletarTransacao();
}
