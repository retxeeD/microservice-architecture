package ms.studies.bookmicrosservice.controller;

import jakarta.validation.Valid;
import ms.studies.bookmicrosservice.dto.BookRequestDto;
import ms.studies.bookmicrosservice.dto.BookResponseDto;
import ms.studies.bookmicrosservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/book")
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDto save(@RequestBody @Valid BookRequestDto book) {
        return service.save(book);
    }

    @GetMapping("/consult/{bookNumber}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponseDto consult(@PathVariable Integer bookNumber){
        return service.consult(bookNumber);
    }

    @GetMapping("/consult")
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponseDto> consult(){
        return service.consultAll();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        service.deleteBook(id);
    }
}