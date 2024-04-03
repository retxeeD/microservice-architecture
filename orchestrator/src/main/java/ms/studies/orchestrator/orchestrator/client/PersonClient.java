package ms.studies.orchestrator.orchestrator.client;

import ms.studies.orchestrator.orchestrator.dto.PersonRequest;
import ms.studies.orchestrator.orchestrator.dto.RentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "person-microsservice", url = "http://localhost:8080")
public interface PersonClient {

    @PostMapping("/v1/person/register")
    String registerPerson(@RequestBody PersonRequest personDto);

    @PutMapping("/v1/person/rent-book")
    String rentBook(@RequestBody RentRequest rentDto);

    @PutMapping("/v1/person/return-book")
    String returnBook(@RequestBody RentRequest rentDto);

}
