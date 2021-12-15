package br.com.heycristhian.producthexagonal.adapters.inbound.controller.handler;

import br.com.heycristhian.producthexagonal.adapters.inbound.dto.response.ExceptionResponse;
import br.com.heycristhian.producthexagonal.application.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var response = ExceptionResponse.builder()
                .code(httpStatus.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("There is an error in the system")
                .build();

        log.error("Internal Server Error: {}", e.getLocalizedMessage());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleProductNotFoundException(ProductNotFoundException e) {
        var httpStatus = HttpStatus.NOT_FOUND;
        var response = ExceptionResponse.builder()
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .message(e.getLocalizedMessage())
                .build();

        log.error("Not found Exception: {}", response);
        return ResponseEntity.status(httpStatus).body(response);
    }
}
