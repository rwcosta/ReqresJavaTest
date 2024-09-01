package errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum Errors {
    EXCEPTION_MIN_VALUE("Value must be bigger than or equal %s."),
    EXCEPTION_MAX_VALUE("Value must be less than or equal %s."),
    INVALID_FORMAT_EXCEPTION("Value must be of type '%s'"),
    USER_NOT_FOUND("User not found");

    private final String errorMessage;

    public static String getMessage(String message, List<String> values) {
        return formatErrorMessage(valueOf(message).getErrorMessage(), values);
    }

    public static String formatErrorMessage(String message, List<String> values) {
        return (Objects.isNull(values) || values.isEmpty()) ? message : message.formatted(values.toArray());
    }
}
