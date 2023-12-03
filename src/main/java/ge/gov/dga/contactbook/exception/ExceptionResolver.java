package ge.gov.dga.contactbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorObject eObject = new ErrorObject();
        eObject.setStatus(HttpStatus.NOT_FOUND.value());
        eObject.setMessage(ex.getMessage());
        eObject.setTimestamp(Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.of("UTC")).toLocalDateTime());
        return new ResponseEntity<>(eObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorObject eObject = new ErrorObject();
        eObject.setStatus(HttpStatus.BAD_REQUEST.value());
        eObject.setMessage("Validation failed");
        eObject.setTimestamp(Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.of("UTC")).toLocalDateTime());
        eObject.setDetails(errors);
        return new ResponseEntity<>(eObject, HttpStatus.BAD_REQUEST);
    }
}
