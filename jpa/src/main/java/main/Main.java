package main;

import config.DbConfig;
import config.HsqlDataSource;
import dao.PersonDao;
import model.Address;
import model.Person;
import model.Phone;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx =
              new AnnotationConfigApplicationContext(
                      DbConfig.class, HsqlDataSource.class);

        PersonDao dao = ctx.getBean(PersonDao.class);


        Person alice = new Person("Alice");
        alice.setAddress(new Address("Pine street 1"));

        alice.setPhones(List.of(new Phone("123213"), new Phone("1222312")));

        dao.save(alice);

//        Optional<Person> alice = dao.findPersonByName("Alice");
//
//        alice.ifPresent(a -> {
//            a.setName("Carol");
//            dao.save(a);
//        });

        System.out.println(dao.findAll());

    }
}