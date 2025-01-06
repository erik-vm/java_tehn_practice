package app;

import java.util.*;
import java.util.stream.Collectors;

import model.Post;
import org.springframework.stereotype.Repository;

@Repository
public class PostMemoryDao {

    private Long counter = 1L;

    private List<Post> postStore = new ArrayList<>();

    public PostMemoryDao() {
        postStore.add(new Post(counter++, "Post 1", "Post 1 content"));
        postStore.add(new Post(counter++, "Post 2", "Post 2 content"));
    }

    public Post save(Post post) {
        if (post.getId() != null) {
            delete(post.getId());
        } else {
            post.setId(counter++);
        }
        postStore.add(post);

        return post;
    }

    public List<Post> findAll() {
        return Collections.unmodifiableList(postStore);
    }

    public void delete(Long postId) {
        postStore = postStore.stream()
                .filter(p -> !postId.equals(p.getId()))
                .collect(Collectors.toList());
    }

    public Post findById(Long postId) {
        return postStore.stream()
                .filter(p -> postId.equals(p.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("can't find: " + postId));
    }
}
