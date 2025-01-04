package demo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class RealPersonDao implements PersonDao {

    @Override
    public String getPersonName(Long id) {
        return "Bob from real database";
    }

}
