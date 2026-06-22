package br.com.stockFlow.dto;

import br.com.stockFlow.Model.Product;
import br.com.stockFlow.Model.User;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record MovimentationDTO(

        @NotNull(message = "Movimentation ID is required")
        UUID movimentationId,

        @NotNull(message = "Quantity is required")
        Integer quantity,

        @NotNull(message = "Date is required")
        LocalDateTime date,

        @NotNull(message = "Product is required")
        Product product,

        @NotNull(message = "User is required")
        User user
) {
}
