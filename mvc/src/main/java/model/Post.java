package model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private Long id;

    @NotNull
    @Size(min= 2, max= 50)
    private String title;
    @NotNull
    @Size(min= 2, max= 500)
    private String text;

}
