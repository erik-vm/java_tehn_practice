package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Post;

import java.util.Arrays;
import java.util.List;

public class JsonConverterList {

    public static void main(String[] args) throws JsonProcessingException {

        String json = "[{\"id\": 1}, {\"title\": \"hello\"}]";

        // Approach nr1
        List<Post> posts = new ObjectMapper().readValue(json, new TypeReference<List<Post>>() {
        });

        System.out.println(posts);


        // Approach nr2
        Post[] posts1 = new ObjectMapper().readValue(json, Post[].class);

        System.out.println(Arrays.toString(posts1));

    }


}
