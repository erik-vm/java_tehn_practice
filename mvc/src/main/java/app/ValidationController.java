package app;

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

        ValidationErrors errors = new ValidationErrors();

        // validation code goes here

        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("validation")
    public void validation(@RequestBody @Valid Post post) {
        System.out.println(post);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrors handleValidationError(
            MethodArgumentNotValidException exception) {

        List<FieldError> errors = exception.getBindingResult().getFieldErrors();

        System.out.println(errors);

        ValidationErrors result = new ValidationErrors();

        // add errors to the result

        return result;
    }

}