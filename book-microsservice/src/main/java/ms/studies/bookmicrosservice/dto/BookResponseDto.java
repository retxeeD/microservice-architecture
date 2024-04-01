package ms.studies.bookmicrosservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BookResponseDto{
        private UUID id;
        private String name;
        private Integer number;

        public BookResponseDto(UUID uuid, String name, Integer number) {
                this.id = uuid;
                this.name = name;
                this.number = number;
        }

        public BookResponseDto(){};
}
