package demo;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx =
                new AnnotationConfigApplicationContext(DemoConfig.class);

        try (ctx) {

            TestPersonDao dao = ctx.getBean(TestPersonDao.class);

            System.out.println(dao);
            System.out.println(dao.getPersonName(1L));

        }
    }



}

