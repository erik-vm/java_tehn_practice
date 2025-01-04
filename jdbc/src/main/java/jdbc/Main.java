package jdbc;

import util.ConnectionInfo;
import util.ConfigUtil;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws Exception {

        ConnectionInfo connectionInfo = ConfigUtil.readConnectionInfo();

        Connection conn = DriverManager.getConnection(
                connectionInfo.getUrl(),
                connectionInfo.getUser(),
                connectionInfo.getPass());

        try (conn; Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("select 42");

            if (!rs.next()) {
                throw new RuntimeException("unexpected error");
            }

            System.out.println(rs.getInt(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
