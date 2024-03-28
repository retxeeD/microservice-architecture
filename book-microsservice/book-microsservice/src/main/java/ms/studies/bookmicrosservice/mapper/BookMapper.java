package ms.studies.bookmicrosservice.mapper;

import ms.studies.bookmicrosservice.dto.BookRequestDto;
import ms.studies.bookmicrosservice.dto.BookResponseDto;
import ms.studies.bookmicrosservice.entity.Book;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface BookMapper {

    Book bookRequestDtoToBook(BookRequestDto bookRequestDto);

    BookResponseDto bookToBookResponseDto(Book bookSaved);
}
