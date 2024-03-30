package ms.studies.bookmicrosservice.service;

import jakarta.persistence.EntityNotFoundException;
import ms.studies.bookmicrosservice.dto.BookRequestDto;
import ms.studies.bookmicrosservice.dto.BookResponseDto;
import ms.studies.bookmicrosservice.entity.Book;
import ms.studies.bookmicrosservice.mapper.BookMapper;
import ms.studies.bookmicrosservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    @Autowired
    private BookMapper mapper;

    public BookResponseDto save(BookRequestDto bookRequestDto){
        Book book = mapper.bookRequestDtoToBook(bookRequestDto);
        Book bookSaved = repository.save(book);
        return mapper.bookToBookResponseDto(bookSaved);
    }

    public BookResponseDto consult(Integer bookNumber){
        Optional<Book> consultResult = Optional.ofNullable(repository.findByNumber(bookNumber)
                .orElseThrow(
                        () -> new EntityNotFoundException("O livro de número '"+ bookNumber +"' não foi encontrado.")
                ));
        BookResponseDto book = mapper.bookToBookResponseDto(consultResult.orElse(null));
        return book;
    }

    public List<BookResponseDto> consultAll(){
        List<Book> bookList = repository.findAll();
        return bookList.stream()
                .map( book -> new BookResponseDto(book.getId(), book.getName(), book.getNumber()))
                .collect(Collectors.toList());
    }

    public void deleteBook(UUID id){
        repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("O livro de ID " + id + " não foi encontrado.")
        );
        repository.deleteById(id);
    }

}
