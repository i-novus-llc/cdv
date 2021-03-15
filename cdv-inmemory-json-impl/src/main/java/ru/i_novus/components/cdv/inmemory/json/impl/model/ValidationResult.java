package ru.i_novus.components.cdv.inmemory.json.impl.model;

import ru.i_novus.components.cdv.core.model.StatusEnum;

import java.util.Objects;

/**
 * Результат валидации.
 */
public class ValidationResult {

    private final String field;

    private final String code;

    private final String language;

    private final String message;

    private final StatusEnum status;

    public ValidationResult(String field, String code, String language,
                            String message, StatusEnum status) {
        this.field = field;
        this.code = code;
        this.language = language;

        this.message = message;
        this.status = status;
    }

    public String getField() {
        return field;
    }

    public String getCode() {
        return code;
    }

    public String getLanguage() {
        return language;
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
                Objects.equals(language, that.language) &&
                Objects.equals(message, that.message) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, code, language, message, status);
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
                (field != null ? "field='" + field + '\'' : "") +
                (code != null ? ", code='" + code + '\'' : "") +
                (language != null ? ", language='" + language + '\'' : "") +
                (message != null ? ", message='" + message + '\'' : "") +
                ", status=" + status +
                '}';
    }
}
