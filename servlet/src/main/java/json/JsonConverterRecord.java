package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Person;

public class JsonConverterRecord {

    public static void main(String[] args) throws JsonProcessingException {

        Person person = new Person(1L, "Alice", 20);

        // a) Object -> Json

        String json = new ObjectMapper().writeValueAsString(person);
        System.out.println(json);


        // b) Json -> Object

        Person alice = new ObjectMapper().readValue(json, Person.class);

        System.out.println(alice);

    }


}
