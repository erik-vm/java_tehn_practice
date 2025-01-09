package jdbc;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        OrderDao orderDao = new OrderDao(PoolProvider.getPool());

        Order order = new Order("myOrder");
        order.setOrderRows(List.of(
                new OrderRow("r1", 20),
                new OrderRow("r2", 25)));

        Order returned = orderDao.insertOrder(order);

        System.out.println(returned);
    }



}
