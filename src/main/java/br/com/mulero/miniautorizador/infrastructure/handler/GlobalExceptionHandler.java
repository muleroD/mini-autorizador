package br.com.mulero.miniautorizador.infrastructure.handler;

import br.com.mulero.miniautorizador.infrastructure.config.I18nConfig;
import br.com.mulero.miniautorizador.infrastructure.exception.CardAlreadyExistsException;
import br.com.mulero.miniautorizador.infrastructure.exception.TransactionException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ResourceBundle;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(I18nConfig.DEFAULT_DIRECTORY,
            LocaleContextHolder.getLocale());

    @ExceptionHandler(CardAlreadyExistsException.class)
    public ResponseEntity<Object> handleCardAlreadyExistsException(CardAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getCardDTO());
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ProblemDetail> handleTransactionalException(TransactionException ex) {
        String message = resourceBundle.getString(ex.getMessage());
        return buildProblemDetail(HttpStatus.UNPROCESSABLE_ENTITY, message, ex);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolationException(ConstraintViolationException ex) {
        return buildProblemDetail(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String messages = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .reduce((m1, m2) -> m1 + " | " + m2)
                .orElse(ex.getMessage());

        return buildProblemDetail(HttpStatus.BAD_REQUEST, messages, ex);
    }

    private ResponseEntity<ProblemDetail> buildProblemDetail(HttpStatus status, String message, Exception ex) {
        log.error(message, ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        return new ResponseEntity<>(problemDetail, status);
    }

}
