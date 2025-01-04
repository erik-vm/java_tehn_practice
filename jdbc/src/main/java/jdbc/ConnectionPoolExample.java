package jdbc;

import org.apache.commons.dbcp2.BasicDataSource;
import util.ConnectionPoolFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolExample {

    public static void main(String[] args) throws SQLException {

        DataSource pool = new ConnectionPoolFactory().createConnectionPool();

        printPoolInfo(pool);

        Connection c1 = pool.getConnection();
        Connection c2 = pool.getConnection();

        printPoolInfo(pool);

        c1.close();

        printPoolInfo(pool);
    }

    private static void printPoolInfo(DataSource dataSource) {
        if (!(dataSource instanceof BasicDataSource)) {
            throw new IllegalArgumentException("argument must be BasicDataSource");
        }

        BasicDataSource pool = (BasicDataSource) dataSource;

        System.out.printf("active: %s; idle: %s\n",
                pool.getNumActive(), pool.getNumIdle());
    }

}
