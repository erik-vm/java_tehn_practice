package model;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Phone {

    @NonNull
    private String number;

}
