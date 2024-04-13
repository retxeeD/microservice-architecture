package ms.studies.orchestrator.orchestrator.advice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import ms.studies.orchestrator.orchestrator.exception.ApiErrors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> feingReturnError(FeignException ex){
        return new ResponseEntity<>(ex.contentUTF8(), HttpStatusCode.valueOf(ex.status()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request){
        BindingResult bindingResult = ex.getBindingResult();
        List<String> messages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new ApiErrors(messages, request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> NotReadableValueError(HttpMessageNotReadableException ex, HttpServletRequest request){
        Throwable rootCause = ex.getRootCause();
        if (rootCause instanceof JsonMappingException jsonMappingException) {
            String fieldName =  jsonMappingException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .reduce((first, second) -> first + "." + second)
                    .orElse(null);
            String[] fieldDataType = ((InvalidFormatException) jsonMappingException).getTargetType().getName().split("\\.");
            return new ResponseEntity<>(
                    new ApiErrors("O campo '"+ fieldName +"' aceita somente dados do tipo "+ fieldDataType[fieldDataType.length -1] +".", request.getRequestURI())
                    , HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>( new ApiErrors("Revise a documentação, há campos sendo enviados com tipo incorreto.", request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }
}
