package uasz.sn.Gestion_Enseignement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException exception){
        return ErrorHttpResponse.response(HttpStatus.NOT_FOUND, exception.getMessage(),null);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public  ResponseEntity<Object> resourceAlreadyExisteException(ResourceAlreadyExistException exception){
        return ErrorHttpResponse.response(HttpStatus.CONFLICT, exception.getMessage(), null);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runtuneExceptionHandler(RuntimeException exception){
        return ErrorHttpResponse.response(HttpStatus.FORBIDDEN, exception.getMessage(), null);
    }
}
