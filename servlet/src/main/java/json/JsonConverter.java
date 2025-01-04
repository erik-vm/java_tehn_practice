package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Post;

import java.util.Arrays;

public class JsonConverter {

    public static void main(String[] args) throws JsonProcessingException {

        Post post = new Post();
        post.setId(1L);
        post.setTitle("Covert Json");
        post.setHidden(false);
        post.setTags(Arrays.asList("java", "json"));

        // a) Object -> Json
         String json = new ObjectMapper().writeValueAsString(post);
        System.out.println(json);

        // b) Json -> Object
         Post post2 = new ObjectMapper().readValue(json, Post.class);

        System.out.println(post2);

    }


}
