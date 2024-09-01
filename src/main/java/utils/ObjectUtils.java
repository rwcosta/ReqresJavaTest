package utils;

import java.util.Arrays;
import java.util.Objects;

public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {
    @SafeVarargs
    public static <T> T coalesce(T... params) {
        return Arrays
                .stream(params)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
