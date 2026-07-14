package br.com.stockFlow.dto;

import br.com.stockFlow.Model.Product;
import br.com.stockFlow.Model.User;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseMovimentationDTO(


        UUID movimentationId,

        Integer quantity,

        LocalDateTime date,

        Product product,

        String userName
) {
}
