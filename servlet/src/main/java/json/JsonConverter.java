package json;

import model.Post;

import java.util.Arrays;

public class JsonConverter {

    public static void main(String[] args) {

        Post post = new Post();
        post.setId(1L);
        post.setTitle("Covert Json");
        post.setHidden(false);
        post.setTags(Arrays.asList("java", "json"));

        // a) Object -> Json
        // String json = new ObjectMapper()...

        // b) Json -> Object
        // Post post = ...

    }


}
