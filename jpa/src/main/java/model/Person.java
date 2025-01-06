package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "isik")
public class Person  extends BaseEntity{

    @NonNull
    @Column(name = "nimi")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aadressi_id")
    private Address address;


    @ElementCollection(fetch = FetchType.EAGER )
    @CollectionTable(name = "isiku_telefonid", joinColumns =
    @JoinColumn(name = "isiku_id", referencedColumnName = "id"))
    private List<Phone> phones;


}
