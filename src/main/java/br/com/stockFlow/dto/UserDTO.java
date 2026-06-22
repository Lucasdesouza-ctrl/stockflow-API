package br.com.stockFlow.dto;

import br.com.stockFlow.enums.RolesEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record UserDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Password is required")
        String password,

        @NotNull(message = "Role is required")
        RolesEnum role) {

}
