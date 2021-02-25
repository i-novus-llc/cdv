package ru.i_novus.components.cdv.inmemory.json.impl.model;

import ru.i_novus.components.cdv.core.api.model.StatusEnum;

import java.util.Objects;

/**
 * Результат валидации.
 */
public class ValidationResult {

    private final String field;

    private final String code;

    private final String message;

    private final StatusEnum status;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidationResult that = (ValidationResult) o;
        return Objects.equals(field, that.field) &&
                Objects.equals(code, that.code) &&
                Objects.equals(message, that.message) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, code, message, status);
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
                (field != null ? "field='" + field + '\'' : "") +
                (code != null ? ", code='" + code + '\'' : "") +
                (message != null ? ", message='" + message + '\'' : "") +
                ", status=" + status +
                '}';
    }
}
