package br.com.stockFlow.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Constants {

    MSG_NOT_FOUND("Registration not found"),
    MSG_INSUFFICIENT_STOCK("Insufficient stock"),
    MSG_USER_ALREADY_EXISTS("Username already exists");

    private final String value;
}
