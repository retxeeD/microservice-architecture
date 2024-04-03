package ms.studies.orchestrator.orchestrator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class RentDto {

    @CPF(message = "O campo 'personDocument' deve ser informado com valor válido.")
    @NotBlank(message = "O campo 'document' é obrigatório.")
    String personDocument;

    @Positive(message = "O campo 'number' aceita somente numeros maiores que 0.")
    @NotNull(message = "O campo 'number' é obrigatório.")
    Integer bookNumber;
}
