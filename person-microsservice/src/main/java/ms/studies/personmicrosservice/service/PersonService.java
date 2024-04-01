package ms.studies.personmicrosservice.service;

import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import ms.studies.personmicrosservice.dto.PersonRequestDto;
import ms.studies.personmicrosservice.dto.PersonResponseDto;
import ms.studies.personmicrosservice.dto.RentBookRequestDto;
import ms.studies.personmicrosservice.entity.Person;
import ms.studies.personmicrosservice.exception.BookRentErrors;
import ms.studies.personmicrosservice.exception.ConsultNotFoundBook;
import ms.studies.personmicrosservice.client.RemoteServicesClient;
import ms.studies.personmicrosservice.mapper.PersonMapper;
import ms.studies.personmicrosservice.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonMapper mapper;

    @Autowired
    private RemoteServicesClient feignClient;

    public PersonResponseDto save(PersonRequestDto personRequestDto) {
        Person person = mapper.personRequestDtoToPerson(personRequestDto);
        return mapper.personToPersonResponseDto(repository.save(person));
    }

    public PersonResponseDto consult(String personDocument) {
        Optional<Person> consultResult =Optional.ofNullable(repository.findByDocument(personDocument)
                .orElseThrow(
                        () -> new EntityNotFoundException("A pessoa de documento '"+ personDocument +"' não foi encontrada.")
                ));
        return mapper.personToPersonResponseDto(consultResult.orElse(null));
    }

    public void delete(UUID id) {
        repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("A pessoa do '"+ id +"' não foi encontrada.")
        );
        repository.deleteById(id);
    }

    public void rentBook(RentBookRequestDto rentBook) throws Exception {
        PersonResponseDto person = consult(rentBook.getDocument());

        if (person.getRentBook() != null){
            throw new BookRentErrors("A pessoa '"+ rentBook.getDocument() +"' nao pode alugar outor livro pois ja possui o livro '"+ person.getRentBook() +"' registrado.");
        }
        try {
            feignClient.getBook( rentBook.getRentBook());
        }catch (FeignException ex){
            throw new ConsultNotFoundBook(ex.contentUTF8(), HttpStatus.NOT_FOUND);
        }

        if (repository.updateUserNameById(rentBook.getDocument(), rentBook.getRentBook()) != 1){
            throw new BookRentErrors("Ocorreu um erro ao alugar o livro, tente novamente.");
        }
    }

    public void returnBook(RentBookRequestDto rentBook) throws BookRentErrors {
        PersonResponseDto person = consult(rentBook.getDocument());
        if(person.getRentBook() == null){
            throw new BookRentErrors("Esta pessoa nao possui nenhum livro para devolver.");
        }
        else if (!person.getRentBook().equals(rentBook.getRentBook())){
            throw new BookRentErrors("O livro que essa pessoa deve devolver é o de numero '"+ person.getRentBook() +"'.");
        }
        if (repository.updateUserNameById(rentBook.getDocument(), null) != 1){
            throw new BookRentErrors("Ocorreu um erro ao devolver o livro, tente novamente.");
        }
    }
}
