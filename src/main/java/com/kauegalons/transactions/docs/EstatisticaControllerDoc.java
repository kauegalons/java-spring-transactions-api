package com.kauegalons.transactions.docs;

import com.kauegalons.transactions.dto.EstatisticaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Statistics", description = "Real-time statistics of recent transactions")
public interface EstatisticaControllerDoc {

    @Operation(
            summary = "Get statistics of recent transactions",
            description = """
                    Calculates `count`, `sum`, `avg`, `min` and `max` considering only transactions whose
                    `dataHora` falls within the configured time window (default: last 60 seconds,
                    property `estatistica.segundos`). Transactions exactly at the window boundary are included.

                    `avg` is rounded to 3 decimal places using `HALF_EVEN`.
                    If there are no transactions in the window, all values are returned as 0."""
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Statistics successfully calculated.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EstatisticaDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Transactions in the window",
                                            value = """
                                                    {
                                                      "count": 10,
                                                      "sum": 1234.56,
                                                      "avg": 123.456,
                                                      "min": 12.34,
                                                      "max": 123.56
                                                    }"""
                                    ),
                                    @ExampleObject(
                                            name = "No transactions in the window",
                                            value = """
                                                    {
                                                      "count": 0,
                                                      "sum": 0,
                                                      "avg": 0,
                                                      "min": 0,
                                                      "max": 0
                                                    }"""
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "406", description = "Not acceptable: the client must accept `application/json` responses.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error while processing the request.", content = @Content)
    })
    ResponseEntity<EstatisticaDTO> getEstatistica();
}
