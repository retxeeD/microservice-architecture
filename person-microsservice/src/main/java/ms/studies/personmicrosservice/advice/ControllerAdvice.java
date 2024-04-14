package ms.studies.personmicrosservice.advice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import ms.studies.personmicrosservice.exception.ApiErrors;
import ms.studies.personmicrosservice.exception.BookRentErrors;
import ms.studies.personmicrosservice.exception.ConsultNotFoundBook;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request){
        BindingResult bindingResult = ex.getBindingResult();
        List<String> messages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ApiErrors(messages, request.getRequestURI());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationErrors(HandlerMethodValidationException ex, HttpServletRequest request){
        Object[] detailMessageArguments = ex.getDetailMessageArguments();
        Optional<Object> valueOptional = Arrays.stream(detailMessageArguments)
                .findFirst();
        return new ApiErrors(valueOptional.get().toString(), request.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiErrors duplicateBookNumberError(HttpServletRequest request){
        return new ApiErrors("O campo 'documento' da pessoa não pode ser duplicado na base de dados, insira outro valor.", request.getRequestURI());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors NotReadableError(HttpMessageNotReadableException ex, HttpServletRequest request){
        Throwable rootCause = ex.getRootCause();
        if (rootCause instanceof JsonMappingException jsonMappingException) {
            String fieldName =  jsonMappingException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .reduce((first, second) -> first + "." + second)
                    .orElse(null);
            String[] fieldDataType = ((InvalidFormatException) jsonMappingException).getTargetType().getName().split("\\.");

            return new ApiErrors("O campo '"+ fieldName +"' aceita somente dados do tipo "+ fieldDataType[fieldDataType.length -1] +".", request.getRequestURI());
        }
        return new ApiErrors("Revise a documentação, há campos sendo enviados com tipo incorreto.", request.getRequestURI());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors notFoundBookError(EntityNotFoundException ex, HttpServletRequest request){
        return new ApiErrors(ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors typeMismatchError(MethodArgumentTypeMismatchException ex, HttpServletRequest request){
        String[] paramType = ex.getRequiredType().toString().split("\\.");
        return new ApiErrors("O parametro '"+ ex.getName() +"' aceita somente dados do tipo "+ paramType[paramType.length -1] +".", request.getRequestURI());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors missingPathVariable(MissingPathVariableException ex, HttpServletRequest request){
        return new ApiErrors("O parametro '"+ ex.getVariableName() +"' é obrigatório na rota.", request.getRequestURI());
    }

    @ExceptionHandler(BookRentErrors.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiErrors bookRentErrors(BookRentErrors ex, HttpServletRequest request){
        return new ApiErrors(ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(ConsultNotFoundBook.class)
    public ResponseEntity<Object> consultNotFoundBook(ConsultNotFoundBook ex) {
        return new ResponseEntity<>(ex.getErrors(), ex.getStatusCode());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors invalidContentType( HttpServletRequest request){
        return new ApiErrors("O header 'Content-Type' recebe apenas o valor  'application/json'", request.getRequestURI());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ApiErrors genericError(HttpServletRequest request){
        return new ApiErrors("Ocorreu um erro inesperado, estamos trabalhando para resolver.", request.getRequestURI());
    }
}
