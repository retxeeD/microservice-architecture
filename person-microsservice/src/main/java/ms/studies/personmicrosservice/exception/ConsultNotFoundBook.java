package ms.studies.personmicrosservice.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConsultNotFoundBook extends Exception{
    @Getter
    private Map errors;

    @Getter
    private HttpStatus statusCode;

    public ConsultNotFoundBook(String errors, HttpStatus statusCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.errors = objectMapper.readValue(errors, LinkedHashMap.class);
        }catch (JsonProcessingException ex){
        }
        this.statusCode = statusCode;
    }
}
