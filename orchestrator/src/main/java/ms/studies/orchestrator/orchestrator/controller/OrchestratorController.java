package ms.studies.orchestrator.orchestrator.controller;

import jakarta.validation.Valid;
import ms.studies.orchestrator.orchestrator.dto.BookDto;
import ms.studies.orchestrator.orchestrator.dto.PersonDto;
import ms.studies.orchestrator.orchestrator.dto.RentDto;
import ms.studies.orchestrator.orchestrator.service.OrchestratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/orquestrator")
public class OrchestratorController {

    @Autowired
    private OrchestratorService service;

    @PostMapping("/register/person")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerPerson(@RequestBody @Valid PersonDto person){
        return service.registerPerson(person);
    }

    @PostMapping("/register/book")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerBook(@RequestBody @Valid BookDto book){
        return service.registerBook(book);
    }

    @PutMapping("/rent-book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rentBook(@RequestBody @Valid RentDto rentDto){
        service.rentBook(rentDto);
    }

    @PutMapping("/return-book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void returnBook(@RequestBody @Valid RentDto rentDto){
        service.returnBook(rentDto);
    }

    @GetMapping("/consult/books")
    @ResponseStatus(HttpStatus.OK)
    public String consultAllBooks(){
        return service.consultAllBooks();
    }

}
