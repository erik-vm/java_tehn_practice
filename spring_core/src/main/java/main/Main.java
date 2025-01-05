package main;

import config.Config;
import config.HsqlDataSource;
import config.PostgresDataSource;
import model.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.EmailService;
import service.TransferService;

public class Main {

    public static void main(String[] args) {

        var ctx = new AnnotationConfigApplicationContext(Config.class, PostgresDataSource.class, HsqlDataSource.class);

        try (ctx) {
            TransferService ts = ctx.getBean(TransferService.class);

            ctx.getBean(EmailService.class).send("Hello");

            ts.transfer(1, "a", "b");


        }

    }
}