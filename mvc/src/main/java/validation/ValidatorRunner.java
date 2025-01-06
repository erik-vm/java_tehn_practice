package validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Post;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

public class ValidatorRunner {

    public static void main(String[] args) throws JsonProcessingException {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();

        Post post = new Post(1L, "", null);

        var violations = validator.validate(post);

        ValidationErrors errors = new ValidationErrors();

        for (ConstraintViolation<Post> violation : violations) {
//            System.out.println(violation.getMessage());

            errors.addErrorMessage(violation.getMessage());

        }

        String json = new ObjectMapper().writeValueAsString(errors);

        System.out.println(json);

    }


}
