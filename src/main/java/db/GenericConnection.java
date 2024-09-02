package db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericConnection {
    private final DialectEnum dialect;

    public GenericConnection(DialectEnum dialect) {
        this.dialect = dialect;
    }

    public Map<String, Object> getResult(String query) {
        // Implement here the computation of database query with single result
        return new HashMap<>();
    }

    public List<Map<String, Object>> getResults(String query) {
        // Implement here the computation of database query with multiple results
        return List.of(new HashMap<>());
    }

    public void execute(String query) {
        // Implement here the computation of database query with no results (Inserts, Updates, etc...)
    }
}
