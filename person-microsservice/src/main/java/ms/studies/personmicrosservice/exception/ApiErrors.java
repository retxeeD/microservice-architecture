package ms.studies.personmicrosservice.exception;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ApiErrors {

    private List<String> errors;

    public ApiErrors(List<String> errors){
        this.errors = errors;
    }

    public ApiErrors(String message){
        this.errors = Collections.singletonList(message);
    }
}
