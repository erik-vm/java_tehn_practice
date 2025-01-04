package main;

import config.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.TransferService;

public class Main {

    public static void main(String[] args) {

        var ctx = new AnnotationConfigApplicationContext(Config.class);

        try (ctx) {
            TransferService ts = ctx.getBean(TransferService.class);

            System.out.println(ts);
        }

    }
}