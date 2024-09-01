package config.parallel;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.PropertiesConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParallelConfig {
    private static final String PARALLEL_PROPERTY = "parallel";

    private Integer threadCount;

    public static ParallelConfig loadConfig() {
        return new ObjectMapper()
                .convertValue(PropertiesConfig.getProperties().get(PARALLEL_PROPERTY), ParallelConfig.class);
    }
}
