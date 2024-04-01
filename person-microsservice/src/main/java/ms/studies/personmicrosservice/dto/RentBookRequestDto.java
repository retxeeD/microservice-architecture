package ms.studies.personmicrosservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class RentBookRequestDto {

    @CPF(message = "O campo 'documento' deve ser informado com valor válido.")
    private String document;

    @Positive(message = "O campo 'rentBook' aceita somente numeros maiores que 0.")
    @NotNull(message = "O campo 'rentBook' é obrigatório.")
    private Integer rentBook;
}
