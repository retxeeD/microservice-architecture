package ms.studies.bookmicrosservice.model;

import ms.studies.bookmicrosservice.dto.BookRequestDto;
import ms.studies.bookmicrosservice.dto.BookResponseDto;
import ms.studies.bookmicrosservice.entity.Book;
import ms.studies.bookmicrosservice.model.mapper.BookMapperImpl;
import ms.studies.bookmicrosservice.repository.BookRepository;
import ms.studies.bookmicrosservice.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    BookRepository repository;

    @InjectMocks
    BookMapperImpl mapper;

    @InjectMocks
    BookService service;

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

    @Test
    void registerNewBookWithSuccess(){
        BookRequestDto requestDto = requestGenerate();
        Book book = bookGenerate();

        when(repository.save(Mockito.any())).thenReturn(book);

        ReflectionTestUtils.setField(service, "mapper", mapper);
        ReflectionTestUtils.setField(service, "repository", repository);

        BookResponseDto bookSaved = service.save(requestDto);

        assertNotNull(bookSaved.getId());
    }


    private BookRequestDto requestGenerate(){
        return new BookRequestDto("Livro Teste", Math.abs(new Random().nextInt()));
    }

    private Book bookGenerate(){
        Book book = new Book("Livro Teste", Math.abs(new Random().nextInt()));
        book.setId(UUID.randomUUID());
        return book;
    }
}
