package ms.studies.personmicrosservice.mapper;

import ms.studies.personmicrosservice.dto.PersonRequestDto;
import ms.studies.personmicrosservice.dto.PersonResponseDto;
import ms.studies.personmicrosservice.entity.Person;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface PersonMapper {

    Person personRequestDtoToPerson(PersonRequestDto personRequestDto);

    PersonResponseDto personToPersonResponseDto(Person person);
}
