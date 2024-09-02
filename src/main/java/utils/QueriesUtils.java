package utils;

public class QueriesUtils {
    /*
    * Add to the end of the query to fetch a random value
    * PS: Not recommended to large tables with many registers, may cause slowness in the database.
    */
    public static String RANDOM_ORDER_BY = " ORDER BY NEW(ID) ";

    /*
    * Add to the of the clause replacing first param with (WHERE or AND) and the second param with the key column to fetch a random value
    * PS: Not recommended to tables with few registers, may return no results.
    */
    public static String RANDOM_CHECKSUM = " %s (ABS(CAST((BINARY_CHECKSUM(%s, NEWID())) AS int)) %% 100) < 1 ";
}
