package model;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Person {

    private Long id;

    @NonNull
    private String name;
}
