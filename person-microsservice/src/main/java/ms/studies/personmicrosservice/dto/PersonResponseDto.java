package ms.studies.personmicrosservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class PersonResponseDto {

    private UUID id;
    private String document;
    private String name;
    private Integer rentBook;

}
