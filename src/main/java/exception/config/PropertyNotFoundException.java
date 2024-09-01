package exception.config;

public class PropertyNotFoundException extends RuntimeException {
    public PropertyNotFoundException(String property) {
        super("Property: %s no found".formatted(property));
    }
}
