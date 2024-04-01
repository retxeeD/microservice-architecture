package ms.studies.personmicrosservice.controller;

import jakarta.validation.Valid;
import ms.studies.personmicrosservice.dto.PersonRequestDto;
import ms.studies.personmicrosservice.dto.PersonResponseDto;
import ms.studies.personmicrosservice.dto.RentBookRequestDto;
import ms.studies.personmicrosservice.exception.BookRentErrors;
import ms.studies.personmicrosservice.service.PersonService;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/person")
public class PersonController {

    @Autowired
    private PersonService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonResponseDto save(@RequestBody @Valid PersonRequestDto person){
        return service.save(person);
    }

    @GetMapping("/consult/{personDocument}")
    @ResponseStatus(HttpStatus.OK)
    public PersonResponseDto consult(@PathVariable @CPF(message = "Informe documento válido.") String personDocument){
        return service.consult(personDocument);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        service.delete(id);
    }

    @PutMapping("/rent-book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rentBook(@RequestBody RentBookRequestDto rentBook) throws BookRentErrors {
        service.rentBook(rentBook);
    }

    @PutMapping("/return-book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void returnBook(@RequestBody RentBookRequestDto rentBook) throws BookRentErrors {
        service.returnBook(rentBook);
    }

}
