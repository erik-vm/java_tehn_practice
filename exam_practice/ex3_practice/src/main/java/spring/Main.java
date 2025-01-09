package spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class Main {

    public static void main(String[] args) {

        DataSource ds = DataSourceBuilder.buildDataSourceWithUrl("someUrl");

        System.out.println(DataSourceBuilder.isFromBuilder(ds)); // true

        var ctx = new AnnotationConfigApplicationContext(Config.class);

        DataSource dataSource = ctx.getBean(DataSource.class);

    }
}