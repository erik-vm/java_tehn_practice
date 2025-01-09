package test;

import jdbc.OrderDao;
import jdbc.Order;
import jdbc.OrderRow;
import jdbc.PoolProvider;
import org.junit.After;
import org.junit.Test;
import runner.NoPointsIfThisTestFails;
import runner.Points;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class JdbcTest {

    @Test
    @Points(4)
    public void addsOrder() throws Exception {

        OrderDao orderDao = new OrderDao(PoolProvider.getPool());

        orderDao.insertOrder(new Order("myOrder"));

        assertThat(PoolProvider.isConnectionAvailable(), is(true));

        List<Order> ordersFromDb = getOrdersFromDatabase();

        assertThat(ordersFromDb.size(), is(1));
        assertThat(ordersFromDb.get(0).getId(), is(1L));
        assertThat(ordersFromDb.get(0).getName(), is("myOrder"));
    }

    @Test
    @NoPointsIfThisTestFails
    public void throwsOnBadData() throws Exception {

        OrderDao orderDao = new OrderDao(PoolProvider.getPool());

        Order order = new Order("myOrder");
        order.setOrderRows(List.of(new OrderRow("r1", "invalid price")));

        try {
            orderDao.insertOrder(order);

            fail("did not throw");

        } catch (Exception e) {}

        assertThat(PoolProvider.isConnectionAvailable(), is(true));
    }

    @Test
    @Points(5)
    public void addsOrderWithNumber() throws Exception {

        OrderDao orderDao = new OrderDao(PoolProvider.getPool());

        Order inserted = orderDao.insertOrder(new Order("myOrder"));

        assertThat(inserted.getNumber(), is("OR-1001"));

        assertThat(PoolProvider.isConnectionAvailable(), is(true));

        List<Order> ordersFromDb = getOrdersFromDatabase();

        assertThat(ordersFromDb.size(), is(1));
        assertThat(ordersFromDb.get(0).getId(), is(1L));
        assertThat(ordersFromDb.get(0).getName(), is("myOrder"));
        assertThat(ordersFromDb.get(0).getNumber(), is("OR-1001"));
    }

    @Test
    @Points(4)
    public void addsOrderRows() throws Exception {

        OrderDao orderDao = new OrderDao(PoolProvider.getPool());

        Order order = new Order("myOrder");
        order.setOrderRows(List.of(
                new OrderRow("r1", 20),
                new OrderRow("r2", 25)));

        orderDao.insertOrder(order);

        assertThat(PoolProvider.isConnectionAvailable(), is(true));

        List<OrderRow> rows = getOrderRowsFromDatabase();

        assertThat(rows.size(), is(2));
        assertThat(rows.get(0).getItemName(), is("r1"));
        assertThat(rows.get(1).getItemName(), is("r2"));
        assertThat(rows.get(0).getPrice(), is(20));
        assertThat(rows.get(1).getPrice(), is(25));
    }

    @Test
    @Points(2)
    public void doNotInsertPartialData() throws Exception {

        // test addsOrderRows() should pass before this test counts.
        addsOrderRows(); resetPool();

        OrderDao orderDao = new OrderDao(PoolProvider.getPool());

        Order order = new Order("myOrder");
        order.setOrderRows(List.of(new OrderRow("r1", "invalid price")));

        try {
            orderDao.insertOrder(order);
        } catch (Exception e) {}

        assertThat(PoolProvider.isConnectionAvailable(), is(true));

        List<Order> ordersFromDb = getOrdersFromDatabase();

        assertThat(ordersFromDb.size(), is(0));
    }

    private List<Order> getOrdersFromDatabase() throws Exception {
        String sql = "select * from orders";

        Connection c = PoolProvider.getPool().getConnection();

        ResultSet rs = c.createStatement().executeQuery(sql);

        List<Order> orders = new ArrayList<>();
        while (rs.next()) {
            Order o = new Order(rs.getString(2));
            o.setId(rs.getLong(1));
            o.setNumber(rs.getString(3));
            orders.add(o);
        }

        return orders;
    }

    private List<OrderRow> getOrderRowsFromDatabase() throws Exception {
        String sql = "select * from order_rows";

        Connection c = PoolProvider.getPool().getConnection();

        ResultSet rs = c.createStatement().executeQuery(sql);

        List<OrderRow> rows = new ArrayList<>();
        while (rs.next()) {
            rows.add(new OrderRow(rs.getString(2), rs.getObject(3)));
        }

        return rows;
    }

    @After
    public void resetPool() throws Exception {
        PoolProvider.closePool();
    }

}