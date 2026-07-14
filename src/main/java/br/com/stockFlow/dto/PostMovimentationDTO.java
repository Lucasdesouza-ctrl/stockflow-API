package br.com.stockFlow.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostMovimentationDTO(

        @NotNull(message = "Quantity is required")
        Integer quantity,

        @NotNull(message = "Date is required")
        LocalDateTime date,

        @NotNull(message = "Product ID is required")
        UUID productId,

        @NotNull(message = "User ID is required")
        UUID userId
) {
}
