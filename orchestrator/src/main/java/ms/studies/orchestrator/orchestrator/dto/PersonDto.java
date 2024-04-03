package ms.studies.orchestrator.orchestrator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class PersonDto {

    @CPF(message = "O campo 'personDocument' deve ser informado com valor válido.")
    @NotBlank(message = "O campo 'document' é obrigatório.")
    String personDocument;

    @NotBlank(message = "O campo 'personName' é obrigatório.")
    String personName;
}
