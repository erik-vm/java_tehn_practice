package mvc;

public class Dao {

    public String getOrder(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("not_found");
        }

        return "some order data";
    }

    public String getUserInfo(String username) {
        if ("admin".equals(username)) {
            throw new SecurityException("prohibited");
        }

        return "some user info";
    }

    public String getCustomer(Long id) {
        if (true) {
            throw new RuntimeException("programming error");
        }

        return "some customer info";
    }

}
