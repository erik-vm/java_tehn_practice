package jdbc;

import util.ConfigUtil;
import util.ConnectionInfo;
import util.ConnectionPoolFactory;
import util.FileUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws Exception {

        createSchema();

        DataSource pool = new ConnectionPoolFactory().createConnectionPool();

        PersonDao dao = new PersonDao(pool);

        System.out.println(dao.findPersons());


    }

    private static void createSchema() throws SQLException {

        ConnectionInfo connectionInfo= ConfigUtil.readConnectionInfo();

        Connection conn = DriverManager.getConnection(
                connectionInfo.getUrl(),
                connectionInfo.getUser(),
                connectionInfo.getPass());
        try (conn; Statement stmt = conn.createStatement()) {

            String schema = FileUtil.readFileFromClasspath("schema.sql");
            String data = FileUtil.readFileFromClasspath("data.sql");

            stmt.executeUpdate(schema);
            stmt.executeUpdate(data);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
