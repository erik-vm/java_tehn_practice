package main;

import model.Person;

import java.util.List;

public class PersonDao {

    public List<Person> getAllPersons() {
        String sql = "select id, name from person";

        throw new RuntimeException("not implemented yet");
    }

    public Person insertPerson(Person person) {
        throw new RuntimeException("not implemented yet");
    }

}
