package ms.studies.bookmicrosservice.service;

import ms.studies.bookmicrosservice.dto.BookRequestDto;
import ms.studies.bookmicrosservice.dto.BookResponseDto;
import ms.studies.bookmicrosservice.entity.Book;
import ms.studies.bookmicrosservice.mapper.BookMapper;
import ms.studies.bookmicrosservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
