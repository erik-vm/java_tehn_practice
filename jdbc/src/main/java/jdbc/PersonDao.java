package jdbc;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {

    private final DataSource dataSource;


    public PersonDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    Person insertPerson(Person person) {

        String sql = "insert into person (name, age) values (?, ?)";

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"})) {

            ps.setString(1, person.getName());
            ps.setInt(2, person.getAge());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (!generatedKeys.next()) {
                throw new RuntimeException("no key found");
            }

            return new Person(generatedKeys.getLong("id"), person.getName(), person.getAge());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    Person findPersonById(Long id) {

        String sql = "select id, name, age from person where id = ?";

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Person(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("age"));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    List<Person> findPersons() {

        List<Person> people = new ArrayList<>();

        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("select id, name, age from person");

            while (rs.next()) {
                Person person = new Person(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("age"));
                people.add(person);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }

}
