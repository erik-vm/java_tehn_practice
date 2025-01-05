package main;

import model.Person;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.sun.tools.attach.VirtualMachine.list;
import static org.springframework.jdbc.core.JdbcOperationsExtensionsKt.query;

@Repository
public class PersonDao {

    private JdbcClient jdbcClient;

    public PersonDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Person> getAllPersons() {
        String sql = "select id, name from person";

        return jdbcClient
                .sql(sql)
                .query(Person.class)
                .list();
    }

    public Person insertPerson(Person person) {
        String sql= "insert into person (name) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
               .param(person.getName())
                .update(keyHolder, "id");


        return person.withId(keyHolder.getKey().longValue());

    }

}
