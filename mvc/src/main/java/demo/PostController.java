package demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private PostDao dao;

    public PostController(PostDao dao) {
        this.dao = dao;
    }

    @GetMapping("posts")
    public List<Post> getPosts() {
        return dao.findAll();
    }

    @PostMapping("posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post savePost(@RequestBody Post post) {

        System.out.println("save post: " + post);

        return dao.save(post);
    }
}