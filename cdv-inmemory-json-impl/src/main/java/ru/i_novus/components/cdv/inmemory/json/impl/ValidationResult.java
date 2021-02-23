package ru.i_novus.components.cdv.inmemory.json.impl;

import ru.i_novus.components.cdv.core.StatusEnum;

public class ValidationResult {

    private String field;

    private String code;

    private String message;

    private StatusEnum status;

    public ValidationResult(String field, String code, String message, StatusEnum status) {
        this.field = field;
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getField() {
        return field;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public StatusEnum getStatus() {
        return status;
    }
}
