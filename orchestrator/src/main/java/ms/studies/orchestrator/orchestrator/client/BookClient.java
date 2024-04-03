package ms.studies.orchestrator.orchestrator.client;

import ms.studies.orchestrator.orchestrator.dto.BookRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "book-microsservice", url = "http://localhost:8081")
public interface BookClient {

    @PostMapping("/v1/book/register")
    String registerBook(@RequestBody BookRequest bookDto);

    @GetMapping("/v1/book/consult")
    String consultAllBooks();

}
