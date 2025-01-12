package mvc_exercises.return_input;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//Tehke nii, et kui sellele saata POST päring mistahes sisuga, siis vastab see
//täpselt sama sisuga, mis sisendiks anti.
//
//Kõik rakenduse töötamiseks vajalik peab mahtuma Ctrl klassi.
//
//NB! Nõuded, mille eiramisel lahendust ei arvestatata.
//     - Muuta võite vaid klassi Ctrl.
//     - Rakendus peab töötama ka serveris, mitte ainult läbi testi.
//        - Rakenduse aadress serveris peab olema "/api/echo".

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "mvc_exercises.return_input")
public class OneClassMVC extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{OneClassMVC.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/api/*"};
    }


    @RestController
    @RequestMapping("echo")
    public static class Ctr {
        @GetMapping
        public String hello() {
            return "hello!";
        }

        @PostMapping
        public String returnInput(@RequestBody String input) {
            return input;
        }
    }
}
