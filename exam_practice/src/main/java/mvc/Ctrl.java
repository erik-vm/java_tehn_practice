package mvc;

import org.springframework.web.bind.annotation.GetMapping;

public class Ctrl {

    @GetMapping("/echo")
    public void sayHello() {

    }

}