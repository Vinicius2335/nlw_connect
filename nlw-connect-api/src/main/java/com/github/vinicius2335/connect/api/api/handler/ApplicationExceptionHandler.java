package com.github.vinicius2335.connect.api.api.handler;

import com.github.vinicius2335.connect.api.domain.event.EventNotFoundException;
import com.github.vinicius2335.connect.api.domain.subscription.SubscriptionConflictException;
import com.github.vinicius2335.connect.api.domain.user.UserNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("One or more fields are invalid");

        Map<String, String> fields = ex.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage
                ));
        problemDetail.setProperty("fields", fields);

        return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    @ExceptionHandler({EventNotFoundException.class, UserNotFoundException.class})
    public ProblemDetail handleNotFound(Exception ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Entity not found");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler({SubscriptionConflictException.class})
    public ProblemDetail handleConflict(Exception ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Conflict");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }
}
