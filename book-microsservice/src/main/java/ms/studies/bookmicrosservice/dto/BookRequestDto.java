package ms.studies.bookmicrosservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;
@Data
public class BookRequestDto{

    @NotBlank(message = "O campo 'name' é obrigatório.")
    private String name;

    @Positive(message = "O campo 'number' aceita somente numeros maiores que 0.")
    @NotNull(message = "O campo 'number' é obrigatório.")
    private Integer number;

    public BookRequestDto(String name, Integer number) {
        this.name = name;
        this.number = number;
    }
}
