package ms.studies.bookmicrosservice.mapper;

import ms.studies.bookmicrosservice.dto.BookRequestDto;
import ms.studies.bookmicrosservice.dto.BookResponseDto;
import ms.studies.bookmicrosservice.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookMapperTest {

    @InjectMocks
    BookMapperImpl mapper;

    @BeforeEach
    public void setUp(){
        mapper = new BookMapperImpl();
    }

    @Test
    void testMapperBookRequestDtoToBook(){
        BookRequestDto requestDto = requestGenerate();
        Book book = mapper.bookRequestDtoToBook(requestDto);
        assertEquals(book.getName(), requestDto.getName());
        assertEquals(book.getNumber(), requestDto.getNumber());
    }

    @Test
    void testMapperBookToBookResponseDto(){
        BookRequestDto requestDto = requestGenerate();
        Book book = mapper.bookRequestDtoToBook(requestDto);
        BookResponseDto bookResponseDto = mapper.bookToBookResponseDto(book);
        assertEquals(book.getName(), bookResponseDto.getName());
        assertEquals(book.getNumber(), bookResponseDto.getNumber());
    }

    private BookRequestDto requestGenerate(){
        return new BookRequestDto("Livro Teste", Math.abs(new Random().nextInt()));
    }
}
