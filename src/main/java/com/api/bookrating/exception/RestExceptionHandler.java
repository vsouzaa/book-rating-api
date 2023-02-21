package com.api.bookrating.exception;

import com.api.bookrating.exception.details.ExceptionDetails;
import com.api.bookrating.exception.details.ValidationExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDetails> badRequestHandle(BadRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .error("Bad Request")
                        .status(badRequest.value())
                        .details(e.getMessage())
                        .timeStamp(LocalDateTime.now())
                        .build(), badRequest);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> methodArgumentNotValidHandle(MethodArgumentNotValidException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldsMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .error("Invalid Fields")
                        .status(badRequest.value())
                        .details("Field(s) error(s)")
                        .fields(fields)
                        .fieldMessage(fieldsMessages)
                        .timeStamp(LocalDateTime.now())
                        .build(), badRequest);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionDetails> methodNotAllowedRequestHandle(HttpRequestMethodNotSupportedException e) {
        HttpStatus methodNotAllowed = HttpStatus.METHOD_NOT_ALLOWED;
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .error("Method Not Allowed")
                        .status(methodNotAllowed.value())
                        .details(e.getMessage())
                        .timeStamp(LocalDateTime.now())
                        .build(), methodNotAllowed);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionDetails> NotFoundHandler(NoHandlerFoundException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .error("Not Found")
                        .status(notFound.value())
                        .details(e.getMessage())
                        .timeStamp(LocalDateTime.now())
                        .build(), notFound);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDetails> internalServerErrorHandler(RuntimeException e) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .error("Internal Server Error")
                        .status(internalServerError.value())
                        .details(e.getMessage())
                        .timeStamp(LocalDateTime.now())
                        .build(), internalServerError);
    }
}
