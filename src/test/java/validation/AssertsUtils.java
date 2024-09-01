package validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static validation.Types.TYPE_ARRAY_LIST;
import static validation.Types.TYPE_LINKED_HASH_MAP;

public class AssertsUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void validate(Object actual, Object expected) {
        Map<String, Object> actualMap = objectMapper.convertValue(actual, TreeMap.class);
        Map<String, Object> expectedMap = objectMapper.convertValue(expected, TreeMap.class);

        actualMap.forEach((k, v) -> {
            switch (v.getClass().getTypeName()) {
                case TYPE_LINKED_HASH_MAP -> validate(v, expectedMap.get(k));

                case TYPE_ARRAY_LIST -> {
                    for (int i = 0; i < ((List<Object>) v).size(); i++)
                        validate(v, expectedMap.get(k));
                }

                default -> validateField(actualMap, expectedMap, k);
            }
        });

        Assert.assertTrue(expectedMap.isEmpty(), "Expected fields non present at the Actual object:\n%s".formatted(expectedMap));
    }

    private static void validateField(Map<String, Object> actual, Map<String, Object> expected, String fieldName) {
        Assert.assertEquals(
                actual.get(fieldName),
                expected.get(fieldName),
                "Error on field [%s]: Expected %s but found Actual [%s]".formatted(fieldName, actual.get(fieldName), expected.get(fieldName))
        );

        expected.remove(fieldName);
    }
}
