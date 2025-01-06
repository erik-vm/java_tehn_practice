package app;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import validation.ValidationErrors;

import jakarta.validation.Valid;

import java.util.List;

@RestController
public class ValidationController {

    @PostMapping("manual-validation")
    public ResponseEntity<Object> manualValidation(@RequestBody Post post) {


        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        var violations = validator.validate(post);

        ValidationErrors errors = new ValidationErrors();

        for (ConstraintViolation<Post> violation : violations) {

            errors.addErrorMessage(violation.getMessage());

        }

        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("validation")
    public void validation(@RequestBody @Valid Post post) {
        System.out.println(post);
    }

}