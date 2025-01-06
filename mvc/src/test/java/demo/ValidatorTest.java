package demo;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

public class ValidatorTest {

    public static void main(String[] args) {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();

        Post post = new Post(1L, "", null);

        var violations = validator.validate(post);

        for (ConstraintViolation<Post> violation : violations) {
            System.out.println(violation.getMessage());
            System.out.println(violation.getConstraintDescriptor());
        }

    }


}
