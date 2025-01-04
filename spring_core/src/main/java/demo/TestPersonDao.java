package demo;

public class TestPersonDao implements PersonDao {

    @Override
    public String getPersonName(Long id) {
        return "Alice from test database";
    }

}
