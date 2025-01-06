package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "aadress")
public class Address extends BaseEntity {

    @Column(name = "tanav")
    private String street;

    public Address(String street) {
        this.street = street;
    }
}
