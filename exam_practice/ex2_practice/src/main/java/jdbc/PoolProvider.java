package jdbc;

import lombok.SneakyThrows;
import org.apache.commons.dbcp2.BasicDataSource;
import util.FileUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PoolProvider {

    private static BasicDataSource pool;

    private PoolProvider() {}

    public static DataSource getPool() throws Exception {
        if (pool != null) {
            return pool;
        }

        pool = new BasicDataSource();
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:db1;sql.syntax_pgs=true");
        pool.setMaxTotal(1);
        pool.setInitialSize(1);

        try {
            // has the side effect of initializing the connection pool
            pool.getLogWriter();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Connection connection = pool.getConnection();

        insertSchema(connection);

        connection.close();

        return pool;
    }

    public static boolean isConnectionAvailable() {
        return pool == null || pool.getNumIdle() > 0;
    }

    private static void insertSchema(Connection connection) throws Exception {
        String sql = FileUtil.readFileFromClasspath("schema.sql");
        for (String each : getStatements(sql)) {
            connection.createStatement().executeQuery(each);
        }
    }

    private static List<String> getStatements(String sql) {
        return Arrays.stream(sql.split(";"))
                .filter(line -> !line.trim().isBlank())
                .collect(Collectors.toList());
    }

    public static void closePool() throws SQLException {
        if (pool == null) {
            return;
        }

        pool.close();

        pool = null;
    }
}
