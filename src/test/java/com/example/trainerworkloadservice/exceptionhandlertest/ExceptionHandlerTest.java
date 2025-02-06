package com.example.trainerworkloadservice.exceptionhandlertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.trainerworkloadservice.exceptionhandlers.ExceptionResponse;
import com.example.trainerworkloadservice.exceptionhandlers.RestResponseEntityExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ExtendWith(MockitoExtension.class)
public class ExceptionHandlerTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ConstraintViolation<?> violation;
    @InjectMocks
    private RestResponseEntityExceptionHandler restResponseEntityExceptionHandler;

    @BeforeEach
    public void init() {
        when(request.getRequestURI()).thenReturn("/test-uri");
    }

    @Test
    void testHandleDateFormatException() {
        //when
        ResponseEntity<ExceptionResponse<String>> result =
            restResponseEntityExceptionHandler.handleDateFormatException(request);

        //then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Wrong date format.Correct format is: yyyy-MM-dd",
            Objects.requireNonNull(result.getBody()).getMessage());
    }

    @Test
    void testHandleNotSupportedMethodException() {
        //given
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException("POST");

        //when
        ResponseEntity<ExceptionResponse<String>> result =
            restResponseEntityExceptionHandler.handleNotSupportedMethodException(exception, request);

        //then
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, result.getStatusCode());
        assertEquals("Request method 'POST' is not supported", Objects.requireNonNull(result.getBody()).getMessage());
    }

    @Test
    void testHandleRequestNoHandlerFoundException() {
        //given
        NoHandlerFoundException exception = new NoHandlerFoundException("GET", "/test-uri", new HttpHeaders());

        //when
        ResponseEntity<ExceptionResponse<String>> result =
            restResponseEntityExceptionHandler.handleRequestNoHandlerFoundException(exception, request);

        //then
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("No endpoint GET /test-uri.", Objects.requireNonNull(result.getBody()).getMessage());
    }

    @Test
    void testHandleHttpMessageNotReadableException() {
        //given
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException(
            "Invalid message", mock(HttpInputMessage.class));

        //when
        ResponseEntity<ExceptionResponse<String>> result =
            restResponseEntityExceptionHandler.handleHttpMessageNotReadableException(exception, request);

        //then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Request body is missing or is invalid", Objects.requireNonNull(result.getBody()).getMessage());
    }

    @Test
    void testDateTimeParseException() {
        //given
        HttpMessageNotReadableException exception =
            new HttpMessageNotReadableException("Invalid message",
                mock(DateTimeParseException.class), mock(HttpInputMessage.class));

        //when
        ResponseEntity<ExceptionResponse<String>> result =
            restResponseEntityExceptionHandler.handleHttpMessageNotReadableException(exception, request);

        //then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Wrong date format. Correct format is: yyyy-MM-dd'T'HH:mm:ss",
            Objects.requireNonNull(result.getBody()).getMessage());
    }

    @Test
    void testHandleInvalidRequestExceptions() {
        //given
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        //when
        ResponseEntity<ExceptionResponse<Map<String, String>>> result =
            restResponseEntityExceptionHandler.handleInvalidRequestExceptions(exception, request);

        //then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void testHandleGeneralException() {
        //given
        Exception exception = new Exception("General error");

        //when
        ResponseEntity<ExceptionResponse<String>> result =
            restResponseEntityExceptionHandler.handleGeneralException(exception, request);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("INTERNAL_SERVER_ERROR", Objects.requireNonNull(result.getBody()).getMessage());
    }


    @Test
    void testHandleInvalidRequestParamExceptions() {
        //given
        ConstraintViolationException exception = mock(ConstraintViolationException.class);
        when(exception.getConstraintViolations()).thenReturn(Set.of(violation));
        when(violation.getMessage()).thenReturn("Violation message");

        //when
        ResponseEntity<ExceptionResponse<Set<String>>> result =
            restResponseEntityExceptionHandler.handleInvalidRequestParamExceptions(exception, request);

        //then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(Objects.requireNonNull(result.getBody()).getMessage(), Set.of("Violation message"));
    }

    @Test
    void testHandleMissingServletRequestParameterException() {
        //given
        MissingServletRequestParameterException exception = mock(MissingServletRequestParameterException.class);
        when(exception.getParameterName()).thenReturn("paramName");

        //when
        ResponseEntity<ExceptionResponse<Map<String, String>>> result =
            restResponseEntityExceptionHandler.handleMissingServletRequestParameterException(exception, request);

        //then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertTrue(Objects.requireNonNull(result.getBody()).getMessage().containsKey("paramName"));
    }

}
