package ms.studies.orchestrator.orchestrator.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Getter
public class ApiErrors {

    private List<String> errors;
    private String path;

    public ApiErrors(String message, String path){
        this.errors = Collections.singletonList(message);
        this.path = path;
    }
}
