package ms.studies.orchestrator.orchestrator.service;

import ms.studies.orchestrator.orchestrator.client.BookClient;
import ms.studies.orchestrator.orchestrator.client.PersonClient;
import ms.studies.orchestrator.orchestrator.dto.BookDto;
import ms.studies.orchestrator.orchestrator.dto.PersonDto;
import ms.studies.orchestrator.orchestrator.dto.RentDto;
import ms.studies.orchestrator.orchestrator.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrchestratorService {

    @Autowired
    private PersonClient personClient;

    @Autowired
    private BookClient bookClient;

    @Autowired
    private DtoMapper dtoMapper;



    public String consultAllBooks() {
        return bookClient.consultAllBooks();
    }

    public void returnBook(RentDto rentDto) {
        personClient.returnBook(dtoMapper.rentDtoToRentRequest(rentDto));
    }

    public void rentBook(RentDto rentDto) {
        personClient.rentBook(dtoMapper.rentDtoToRentRequest(rentDto));
    }

    public String registerBook(BookDto book) {
        return bookClient.registerBook(dtoMapper.bookDtoToBookRquest(book));
    }

    public String registerPerson(PersonDto person) {
        return personClient.registerPerson(dtoMapper.personDtoToPersonRequest(person));
    }
}
