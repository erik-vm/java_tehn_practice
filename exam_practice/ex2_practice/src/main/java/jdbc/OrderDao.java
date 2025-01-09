package jdbc;

import javax.sql.DataSource;
import java.sql.*;

public class OrderDao {

    private DataSource dataSource;

    public OrderDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Order insertOrder(Order order) {

        String sql = "insert into orders (name) values (?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.executeUpdate();

            Long orderId = null;

            for (OrderRow orderRow : order.getOrderRows()) {
                insertOrderRow(orderRow, orderId, conn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    public void insertOrderRow(OrderRow row, Long orderId, Connection conn) {

        String sql = "insert into order_rows (order_id, item_name, price) " +
                "values (?, ?, ?)";

        try (conn; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, orderId);
            ps.setString(2, row.getItemName());
            ps.setObject(3, row.getPrice());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (!rs.next()) {
                throw new IllegalStateException();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
