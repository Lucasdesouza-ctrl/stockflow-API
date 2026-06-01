package br.com.stockFlow.dto;

import br.com.stockFlow.enums.RolesEnum;

public record UserDTO (String name, String password, RolesEnum role) {
}
