package demo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("test")
public class TestPostDao implements PostDao {

    @Override
    public Post save(Post post) {
        System.out.println("This is save() on TestPostDao");

        return post;
    }

    @Override
    public List<Post> findAll() {
        return null;
    }
}
