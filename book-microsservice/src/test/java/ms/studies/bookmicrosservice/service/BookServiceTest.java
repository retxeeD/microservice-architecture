package ms.studies.bookmicrosservice.service;

import jakarta.persistence.EntityNotFoundException;
import ms.studies.bookmicrosservice.dto.BookRequestDto;
import ms.studies.bookmicrosservice.dto.BookResponseDto;
import ms.studies.bookmicrosservice.entity.Book;
import ms.studies.bookmicrosservice.mapper.BookMapperImpl;
import ms.studies.bookmicrosservice.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    BookRepository repository;

    @InjectMocks
    BookMapperImpl mapper;

    @InjectMocks
    BookService service;

    @Test
    void registerNewBook(){
        BookRequestDto requestDto = requestGenerate();
        Book book = bookGenerate();

        when(repository.save(Mockito.any())).thenReturn(book);

        ReflectionTestUtils.setField(service, "mapper", mapper);
        ReflectionTestUtils.setField(service, "repository", repository);

        BookResponseDto bookSaved = service.save(requestDto);

        assertNotNull(bookSaved.getId());
    }
    @Test
    void consultBookByNumber(){
        Book book = bookGenerate();
        when(repository.findByNumber(Mockito.any())).thenReturn(Optional.of(book));

        ReflectionTestUtils.setField(service, "mapper", mapper);
        ReflectionTestUtils.setField(service, "repository", repository);

        BookResponseDto bookReturned = service.consult(book.getNumber());
        assertNotNull(bookReturned);
    }
    @Test
    void consultBookByNumberNotExist(){
        Book book = bookGenerate();
        when(repository.findByNumber(Mockito.any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            service.consult(book.getNumber());
        });
    }
    @Test
    void consultAllBooks(){
        Book book = bookGenerate();
        when(repository.findAll()).thenReturn(Stream.of(book).toList());

        ReflectionTestUtils.setField(service, "mapper", mapper);
        ReflectionTestUtils.setField(service, "repository", repository);

        List<BookResponseDto> bookResponseDtoList = service.consultAll();
        assertNotNull(bookResponseDtoList);
    }

    @Test
    void deleteBookById(){
        Book book = bookGenerate();
        ReflectionTestUtils.setField(service, "repository", repository);
        service.deleteBook(book.getId());
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
