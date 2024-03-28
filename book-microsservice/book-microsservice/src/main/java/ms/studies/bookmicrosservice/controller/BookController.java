package ms.studies.bookmicrosservice.controller;

import jakarta.validation.Valid;
import ms.studies.bookmicrosservice.dto.BookRequestDto;
import ms.studies.bookmicrosservice.dto.BookResponseDto;
import ms.studies.bookmicrosservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
