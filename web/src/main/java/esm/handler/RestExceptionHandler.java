package esm.handler;

import esm.dto.BaseExceptionDto;
import esm.exception.*;
import esm.exception.ErrorCodeConstraint;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<?> classNotFoundExceptionHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseExceptionDto(e.getMessage(),
                        ErrorCodeConstraint.INTERNAL_CLASS_NOT_FOUND.code));
    }

    @ExceptionHandler(UnknownDataBaseException.class)
    public ResponseEntity<?> unknownDatabaseExceptionHandler(UnknownDataBaseException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseExceptionDto(e.getMessage(),
                        ErrorCodeConstraint.INTERNAL_DATABASE_ERROR.code));
    }

    @ExceptionHandler({InvalidTagException.class, InvalidCertificateException.class})
    public ResponseEntity<?> invalidCertificateAndTagExceptionHandler(RuntimeException e) {
        return ResponseEntity
                .badRequest()
                .body(new BaseExceptionDto(e.getMessage(), ErrorCodeConstraint.BAD_REQUEST_INPUT.code));
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<?> numberFormatExceptionHandler(NumberFormatException e) {
        return ResponseEntity
                .badRequest()
                .body(new BaseExceptionDto(e.getMessage(),
                        ErrorCodeConstraint.BAD_REQUEST_NUMBER_FORMAT.code));
    }

    @ExceptionHandler(InvalidSortException.class)
    public ResponseEntity<?> numberFormatExceptionHandler(InvalidSortException e) {
        return ResponseEntity
                .badRequest()
                .body(new BaseExceptionDto(e.getMessage(),
                        ErrorCodeConstraint.BAD_REQUEST_SORT_FORMAT.code));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> dataNotFoundExceptionHandler(DataNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new BaseExceptionDto(e.getMessage(), ErrorCodeConstraint.NOT_FOUND.code));
    }

    @ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<?> dataAlreadyExistExceptionHandler(DataAlreadyExistException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new BaseExceptionDto(e.getMessage(), ErrorCodeConstraint.CONFLICT.code));
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<?> PSQLExceptionHandler(PSQLException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new BaseExceptionDto(e.getMessage(), ErrorCodeConstraint.CONFLICT.code));
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> invalidInputException(InvalidInputException e) {
        StringBuilder message = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach((error) ->
                message.append(error.getDefaultMessage()).append("  ")
        );
        return ResponseEntity
                .badRequest()
                .body(new BaseExceptionDto(message.toString(),
                        ErrorCodeConstraint.BAD_REQUEST_INPUT.code));
    }

}
