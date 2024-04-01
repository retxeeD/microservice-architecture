package ms.studies.personmicrosservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class PersonRequestDto {

    @CPF(message = "O campo 'document' deve ser informado com valor válido.")
    @NotBlank(message = "O campo 'document' é obrigatório.")
    private String document;

    @NotBlank(message = "O campo 'name' é obrigatório.")
    private String name;
}
