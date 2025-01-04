package demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

@EnableAspectJAutoProxy
@ComponentScan(basePackages = "demo")
public class DemoConfig {

    @Bean
    @Profile("test")
    public TestPersonDao getPersonDao() {
        return new TestPersonDao();
    }

}




















// set SPRING_PROFILES_ACTIVE=prod