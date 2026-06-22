package br.com.stockFlow.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RolesEnum {

    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String value;

}
