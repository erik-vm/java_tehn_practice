package app;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SampleController {

    @PostMapping("sample")
    public String samplePostHandler(
            @RequestBody Map<String, String> input) {

        return "input was: " + input;
    }

}