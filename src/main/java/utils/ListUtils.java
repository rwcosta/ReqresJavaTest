package utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ListUtils {
    public static <T> T getRandomElement(List<T> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()) % list.size());
    }
}
