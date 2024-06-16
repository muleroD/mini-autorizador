package br.com.mulero.miniautorizador.infrastructure.handler;

import br.com.mulero.miniautorizador.infrastructure.exception.CardAlreadyExistsException;
import br.com.mulero.miniautorizador.infrastructure.exception.TransactionException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static br.com.mulero.miniautorizador.infrastructure.config.I18nConfig.RESOURCE_BUNDLE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String DEFAULT_MESSAGE = "error.internal.server";

    @ExceptionHandler(CardAlreadyExistsException.class)
    public ResponseEntity<Object> handleCardAlreadyExistsException(CardAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getCardDTO());
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ProblemDetail> handleTransactionalException(TransactionException ex) {
        return buildProblemDetail(HttpStatus.UNPROCESSABLE_ENTITY, ex, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolationException(ConstraintViolationException ex) {
        return buildProblemDetail(HttpStatus.BAD_REQUEST, ex, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = RESOURCE_BUNDLE.getString("error.validation.list");

        List<String> errors = ex.getBindingResult().getAllErrors().stream().map(objectError -> {
            String errorMessage = Optional.ofNullable(objectError.getDefaultMessage()).orElse(DEFAULT_MESSAGE);
            return RESOURCE_BUNDLE.getString(errorMessage);
        }).toList();

        return buildProblemDetail(HttpStatus.BAD_REQUEST, ex, message, errors);
    }

    private ResponseEntity<ProblemDetail> buildProblemDetail(HttpStatus status, Exception ex, String message, List<String> errors) {
        log.error(message, ex);

        ProblemDetail problemDetail = getProblemDetail(status, message);
        problemDetail.setProperty("errors", errors);
        return new ResponseEntity<>(problemDetail, status);
    }

    private ResponseEntity<ProblemDetail> buildProblemDetail(HttpStatus status, Exception ex, String message) {
        log.error(message, ex);

        ProblemDetail problemDetail = getProblemDetail(status, message);
        return new ResponseEntity<>(problemDetail, status);
    }

    private ProblemDetail getProblemDetail(HttpStatus status, String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setProperty("timestamp", LocalDateTime.now().toString());
        return problemDetail;
    }
}
