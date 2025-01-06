package app;

import jakarta.validation.Valid;
import model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private PostMemoryDao dao;


    public PostController(PostMemoryDao dao) {
        this.dao = dao;
    }


    @GetMapping("posts")
    public List<Post> getPosts() {
        return dao.findAll();
    }


    @PostMapping("posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post isnertPost(@RequestBody @Valid Post post) {
        return dao.save(post);
    }


    @DeleteMapping("posts/{id}")
    public void deletePost(@PathVariable("id") Long id) {
        dao.delete(id);
    }

//    @ExceptionHandler
//    public String errorHandler(Exception e) {
//        return "internal error";
//    }

}