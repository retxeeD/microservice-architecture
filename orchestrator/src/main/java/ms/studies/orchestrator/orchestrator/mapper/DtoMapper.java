package ms.studies.orchestrator.orchestrator.mapper;

import ms.studies.orchestrator.orchestrator.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    @Mapping(source = "bookName", target = "name")
    @Mapping(source = "bookNumber", target = "number")
    BookRequest bookDtoToBookRquest(BookDto bookDto);

    @Mapping(source = "personDocument", target = "document")
    @Mapping(source = "personName", target = "name")
    PersonRequest personDtoToPersonRequest(PersonDto personDto);

    @Mapping(source = "personDocument", target = "document")
    @Mapping(source = "bookNumber", target = "rentBook")
    RentRequest rentDtoToRentRequest(RentDto rentDto);


}
