package mvc;

import org.springframework.web.bind.annotation.*;

@RestController
public class Ctrl {

    private Dao dao = new Dao();

    @GetMapping("/orders/{id}")
    public String getOrder(@PathVariable Long id) {
        return dao.getOrder(id);
    }

    @GetMapping("/users/{username}")
    public String getOrder(@PathVariable String username) {
        return dao.getUserInfo(username);
    }

    @GetMapping("/customers/{id}")
    public String getCustomer(@PathVariable Long id) {
        return dao.getCustomer(id);
    }
}