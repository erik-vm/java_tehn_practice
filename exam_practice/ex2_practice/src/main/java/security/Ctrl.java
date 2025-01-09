package security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Ctrl {

    @GetMapping("/")
    public String home() {
        return "Home";
    }

    @GetMapping("/security/users/{userName}/info")
    @PreAuthorize("#userName == authentication.name or hasRole('ROLE_ADMIN')")
    public String info(@PathVariable String userName) {
        return "info: " + userName;
    }

}