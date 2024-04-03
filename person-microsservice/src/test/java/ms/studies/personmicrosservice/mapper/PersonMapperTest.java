package ms.studies.personmicrosservice.mapper;

import ms.studies.personmicrosservice.dto.PersonRequestDto;
import ms.studies.personmicrosservice.dto.PersonResponseDto;
import ms.studies.personmicrosservice.entity.Person;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.UUID;

public class PersonMapperTest {

    @InjectMocks
    PersonMapperImpl mapper;

    @BeforeEach
    public void setUp(){
        this.mapper = new PersonMapperImpl();
    }

    @Test
    void testMapperPersonRequestDtoToPerson(){
        PersonRequestDto request = requestGenerate();
        Person person =mapper.personRequestDtoToPerson(request);
        assertEquals(request.getDocument(), person.getDocument());
        assertEquals(request.getName(), person.getName());

    }
    @Test
    void testMapperPersonToPersonResponseDto(){
        PersonRequestDto request = requestGenerate();
        Person person = mapper.personRequestDtoToPerson(request);
        person.setId(UUID.randomUUID());
        person.setRentBook(34);
        PersonResponseDto response= mapper.personToPersonResponseDto(person);
        assertEquals(response.getId(), person.getId());
        assertEquals(response.getName(), person.getName());
        assertEquals(response.getDocument(), person.getDocument());
        assertEquals(response.getRentBook(), person.getRentBook());
    }

    private PersonRequestDto requestGenerate(){
        return new PersonRequestDto("42166595863","Pedro Lima");
    }

}
