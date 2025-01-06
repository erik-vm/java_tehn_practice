package demo;


import java.util.List;

public interface PostDao {
    Post save(Post post);

    List<Post> findAll();
}
