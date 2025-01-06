package app;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class DateController {

    @Data
    public static class SampleDto {
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate date = LocalDate.now();
    }

    @GetMapping("date")
    public SampleDto getDtoWithDateField() {
        return new SampleDto();
    }

    @PostMapping("date")
    public String readAndConvertDateParameter(
            @DateTimeFormat(pattern = "MM-dd-yyyy")
            @RequestParam("date")
            LocalDate date) {

        return "Input was " + date.toString();
    }


}