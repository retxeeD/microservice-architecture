package ms.studies.bookmicrosservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Getter
public class ApiErrors {

    @Getter
    private List<String> errors;
    private String path;

    public ApiErrors(List<String> errors){
        this.errors = errors;
    }

    public ApiErrors(String message, String path){
        this.errors = Collections.singletonList(message);
        this.path = path;
    }}
