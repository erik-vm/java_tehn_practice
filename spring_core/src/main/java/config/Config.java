package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@ComponentScan(basePackages = {"service", "main", "aop"})
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
public class Config {

//    @Bean
//    public TransferService transferService() {
//        return new TransferService();
//    }

    @Bean
    public JdbcClient jdbcClient(DataSource dataSource) {

        var populator = new ResourceDatabasePopulator(
                new ClassPathResource("schema.sql"),
                new ClassPathResource("data.sql"));

        DatabasePopulatorUtils.execute(populator, dataSource);


        return JdbcClient.create(dataSource);
    }

}