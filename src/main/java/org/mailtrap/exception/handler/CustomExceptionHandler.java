package org.mailtrap.exception.handler;

import io.micrometer.common.lang.NonNullApi;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.mailtrap.util.ExceptionMessage;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
@NonNullApi
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request) {
        return buildResponseEntity(ex, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        var errorDetails = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return getResponseEntity(ex, status, request, errorDetails, ExceptionMessage.INPUT_VALIDATION);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
                                                             HttpStatusCode statusCode, WebRequest request) {
        return getResponseEntity(ex, statusCode, request, ex.getMessage(), ExceptionMessage.INTERNAL_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatusCode status,
                                                                         WebRequest request) {
        return getResponseEntity(ex, status, request, ex.getMessage(), ExceptionMessage.METHOD_NOT_SUPPORTED);
    }

    private ResponseEntity<Object> buildResponseEntity(Exception ex, WebRequest request) {
        if (ex instanceof ConstraintViolationException) {

            String errorDetail = ((ConstraintViolationException) ex).getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            return getResponseEntity(ex, HttpStatus.BAD_REQUEST, request, errorDetail,
                    ExceptionMessage.INPUT_VALIDATION);
        } else if (ex instanceof BadRequestException) {
            return getResponseEntity(ex, HttpStatus.BAD_REQUEST, request, ex.getMessage(),
                    ExceptionMessage.BAD_REQUEST_EXCEPTION);
        } else {
            return getResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR, request, ex.getMessage(),
                    ExceptionMessage.INTERNAL_ERROR);
        }
    }

    private ResponseEntity<Object> getResponseEntity(Exception ex, HttpStatusCode status, WebRequest request,
                                                     String errorDetail, String message) {
        var ErrorDto = org.mailtrap.dto.ErrorDto.builder()
                .status(status.value())
                .error(ex.getMessage())
                .message(message)
                .errorDetail(errorDetail)
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(ErrorDto, status);
    }
}