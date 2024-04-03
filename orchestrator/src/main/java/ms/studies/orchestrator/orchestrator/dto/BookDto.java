package ms.studies.orchestrator.orchestrator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class BookDto {

    @NotBlank(message = "O campo 'name' é obrigatório.")
    String bookName;

    @Positive(message = "O campo 'number' aceita somente numeros maiores que 0.")
    @NotNull(message = "O campo 'number' é obrigatório.")
    Integer bookNumber;

}
