package ms.studies.personmicrosservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 11, unique = true)
    private String document;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = true)
    private Integer rentBook;

    public Person(String document, String name) {
        this.document = document;
        this.name = name;
    }
}
