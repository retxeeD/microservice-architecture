package ms.studies.personmicrosservice.advice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.persistence.EntityNotFoundException;
import ms.studies.personmicrosservice.exception.ApiErrors;
import ms.studies.personmicrosservice.exception.BookRentErrors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationErrors(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        List<String> messages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ApiErrors(messages);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationErrors(HandlerMethodValidationException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiErrors duplicateBookNumberError(){
        return new ApiErrors("O campo 'documento' da pessoa não pode ser duplicado na base de dados, insira outro valor.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors NotReadableError(HttpMessageNotReadableException ex){
        Throwable rootCause = ex.getRootCause();
        if (rootCause instanceof JsonMappingException jsonMappingException) {
            String fieldName =  jsonMappingException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .reduce((first, second) -> first + "." + second)
                    .orElse(null);
            String[] fieldDataType = ((InvalidFormatException) jsonMappingException).getTargetType().getName().split("\\.");

            return new ApiErrors("O campo '"+ fieldName +"' aceita somente dados do tipo "+ fieldDataType[fieldDataType.length -1] +".");
        }
        return new ApiErrors("Revise a documentação, há campos sendo enviados com tipo incorreto.");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors notFoundBookError(EntityNotFoundException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors typeMismatchError(MethodArgumentTypeMismatchException ex){
        String[] paramType = ex.getRequiredType().toString().split("\\.");
        return new ApiErrors("O parametro '"+ ex.getName() +"' aceita somente dados do tipo "+ paramType[paramType.length -1] +".");
    }

    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors missingPathVariable(MissingPathVariableException ex){
        return new ApiErrors("O parametro '"+ ex.getVariableName() +"' é obrigatório na rota.");
    }

    @ExceptionHandler(BookRentErrors.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiErrors bookRentErrors(BookRentErrors ex){
        return new ApiErrors(ex.getMessage());
    }

    /**@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ApiErrors genericError(){
        return new ApiErrors("Ocorreu um erro inesperado, estamos trabalhando para resolver.");
    }**/
}
