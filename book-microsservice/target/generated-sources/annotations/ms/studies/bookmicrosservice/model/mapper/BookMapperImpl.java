package ms.studies.bookmicrosservice.model.mapper;

import javax.annotation.processing.Generated;
import ms.studies.bookmicrosservice.model.dto.BookRequestDto;
import ms.studies.bookmicrosservice.model.dto.BookResponseDto;
import ms.studies.bookmicrosservice.model.entity.Book;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-28T16:33:54-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public Book bookRequestDtoToBook(BookRequestDto bookRequestDto) {
        if ( bookRequestDto == null ) {
            return null;
        }

        String name = null;
        int number = 0;

        name = bookRequestDto.getName();
        if ( bookRequestDto.getNumber() != null ) {
            number = bookRequestDto.getNumber();
        }

        Book book = new Book( name, number );

        return book;
    }

    @Override
    public BookResponseDto bookToBookResponseDto(Book bookSaved) {
        if ( bookSaved == null ) {
            return null;
        }

        BookResponseDto bookResponseDto = new BookResponseDto();

        bookResponseDto.setId( bookSaved.getId() );
        bookResponseDto.setName( bookSaved.getName() );
        bookResponseDto.setNumber( bookSaved.getNumber() );

        return bookResponseDto;
    }
}
