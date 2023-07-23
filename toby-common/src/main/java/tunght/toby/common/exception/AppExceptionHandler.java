package tunght.toby.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleForbidden(AccessDeniedException exception) {
        log.error("Exception: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("403 returned");
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorMessages> handleAppException(AppException exception) {
        log.error("Exception: {}", exception.getMessage());
        return responseErrorMessages(List.of(exception.getMessage()), exception.getError().getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessages> handleValidationError(MethodArgumentNotValidException exception) {
        List<String> messages = exception.getBindingResult().getFieldErrors().stream().map(this::createFieldErrorMessage).collect(Collectors.toList());
        log.error("Exception: {}", messages);
        return responseErrorMessages(messages, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessages> handleException(Exception exception) {
        log.error("Exception: ", exception);
        return responseErrorMessages(List.of("internal server error"), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<ErrorMessages> responseErrorMessages(List<String> messages, HttpStatus status) {
        ErrorMessages errorMessages = new ErrorMessages();
        messages.forEach(errorMessages::append);
        return new ResponseEntity<>(errorMessages, status);
    }

    private String createFieldErrorMessage(FieldError fieldError) {
        return fieldError.getDefaultMessage();
    }
}
