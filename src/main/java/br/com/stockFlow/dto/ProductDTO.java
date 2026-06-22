package br.com.stockFlow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "SKU is required")
        String sku,

        @NotBlank(message = "Barcode is required")
        String barcode,

        @NotNull(message = "Quantity is required")
        Integer quantity,

        @NotBlank(message = "Category is required")
        String category
) {
}
