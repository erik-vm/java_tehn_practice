package model;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@With
public class Person {

    private Long id;

    @NonNull
    private String name;
}
