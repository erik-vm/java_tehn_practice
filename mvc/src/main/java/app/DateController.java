package app;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class DateController {

    @Data
    public static class SampleDto {
        private LocalDate date = LocalDate.now();
    }

    @GetMapping("date")
    public SampleDto getDtoWithDateField() {
        return new SampleDto();
    }

    @PostMapping("date")
    public String readAndConvertDateParameter(
            @RequestParam("date")
            LocalDate date) {

        return "Input was " + date.toString();
    }


}