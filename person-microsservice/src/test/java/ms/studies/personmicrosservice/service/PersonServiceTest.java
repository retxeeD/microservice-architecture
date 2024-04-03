package ms.studies.personmicrosservice.service;

import ms.studies.personmicrosservice.client.RemoteServicesClient;
import ms.studies.personmicrosservice.dto.PersonRequestDto;
import ms.studies.personmicrosservice.dto.PersonResponseDto;
import ms.studies.personmicrosservice.dto.RentBookRequestDto;
import ms.studies.personmicrosservice.entity.Person;
import ms.studies.personmicrosservice.mapper.PersonMapperImpl;
import ms.studies.personmicrosservice.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;


import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    PersonRepository repository;

    @Mock
    RemoteServicesClient feignClient;

    @InjectMocks
    PersonMapperImpl mapper;

    @InjectMocks
    PersonService service;

    @Test
    void registerNewPerson(){
        PersonRequestDto request = requestGenerate();
        Person person = personGenerate();

        when(repository.save(Mockito.any())).thenReturn(person);

        ReflectionTestUtils.setField(service, "mapper", mapper);
        ReflectionTestUtils.setField(service, "repository", repository);

        PersonResponseDto response = service.save(request);
        Assertions.assertEquals(request.getName(), response.getName());
        Assertions.assertEquals(request.getDocument(), response.getDocument());
        Assertions.assertNotNull(response.getId());
        Assertions.assertNotNull(response.getRentBook());
    }

    @Test
    void consultPerson(){
        Person person = personGenerate();

        when(repository.findByDocument(Mockito.any())).thenReturn(Optional.of(person));

        ReflectionTestUtils.setField(service, "mapper", mapper);
        ReflectionTestUtils.setField(service, "repository", repository);

        PersonResponseDto response = service.consult(person.getDocument());
        Assertions.assertNotNull(response.getName());
        Assertions.assertNotNull(response.getDocument());
        Assertions.assertNotNull(response.getId());
        Assertions.assertNotNull(response.getRentBook());
    }

    @Test
    void deletePerson(){
        Person person = personGenerate();
        when(repository.findById(Mockito.any())).thenReturn(Optional.of(person));
        ReflectionTestUtils.setField(service, "repository", repository);
        service.delete(person.getId());
    }

    @Test
    void rentBookPerson() throws Exception {
        RentBookRequestDto rentBook = new RentBookRequestDto("42166595863", 1);
        String getBookReturn = "{\"id\":\"27ae80e8-1a6e-4b24-85d9-c80e50a1a965\",\"name\":\"Teste\",\"number\":1}";
        Person person = personGenerate();
        person.setRentBook(null);

        when(repository.findByDocument(Mockito.any())).thenReturn(Optional.of(person));
        when(feignClient.getBook(Mockito.any())).thenReturn(getBookReturn);
        when(repository.updateUserNameById(Mockito.any(), Mockito.any())).thenReturn(1);

        ReflectionTestUtils.setField(service, "mapper", mapper);
        ReflectionTestUtils.setField(service, "repository", repository);
        ReflectionTestUtils.setField(service, "feignClient", feignClient);
        service.rentBook(rentBook);
    }

    @Test
    void returnBookPerson() throws Exception {
        RentBookRequestDto rentBook = new RentBookRequestDto("42166595863", 1);
        Person person = personGenerate();
        when(repository.findByDocument(Mockito.any())).thenReturn(Optional.of(person));
        when(repository.updateUserNameById(Mockito.any(), Mockito.any())).thenReturn(1);
        ReflectionTestUtils.setField(service, "mapper", mapper);
        ReflectionTestUtils.setField(service, "repository", repository);
        service.returnBook(rentBook);
    }



    private PersonRequestDto requestGenerate(){
        return new PersonRequestDto("42166595863","Pedro Lima");
    }

    private Person personGenerate(){
        Person person = mapper.personRequestDtoToPerson(requestGenerate());
        person.setId(UUID.randomUUID());
        person.setRentBook(1);
        return person;
    }

}
