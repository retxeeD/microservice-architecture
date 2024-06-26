package ms.studies.bookmicrosservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, unique = true)
    private Integer number;

    public Book(String name, Integer number) {
        this.name = name;
        this.number = number;
    }
}
