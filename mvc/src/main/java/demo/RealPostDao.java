package demo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Profile("default | prod")
public class RealPostDao implements PostDao {

    private Long counter = 1L;

    private List<Post> postStore = new ArrayList<>();

    public RealPostDao() {
        postStore.add(new Post(counter++, "Post 1", "Post 1 content"));
        postStore.add(new Post(counter++, "Post 2", "Post 2 content"));
    }

    @Override
    public Post save(Post post) {
        System.out.println("This is save() on RealPostDao");

        if (post.getId() != null) {
            delete(post.getId());
        } else {
            post.setId(counter++);
        }
        postStore.add(post);

        return post;
    }

    @Override
    public List<Post> findAll() {
        return Collections.unmodifiableList(postStore);
    }

    public void delete(Long postId) {
        postStore = postStore.stream()
                .filter(p -> !postId.equals(p.getId()))
                .collect(Collectors.toList());
    }

}
