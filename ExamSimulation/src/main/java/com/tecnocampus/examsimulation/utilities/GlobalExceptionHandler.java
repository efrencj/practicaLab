package com.tecnocampus.examsimulation.utilities;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ApiResponse(responseCode = "404", description = "User not found")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerNotFound(NotFoundException ex,
                                         HttpServletRequest request) {
        return new ErrorResponse(ex, request, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InvalidStateChangeException.class)
    @ApiResponse(responseCode = "400", description = "Invalid state change")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerBadRequests(InvalidStateChangeException ex,
                                            HttpServletRequest request) {
        return new ErrorResponse(ex, request,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataException.class)
    @ApiResponse(responseCode = "422", description = "Invalid Data")
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handlerInvalidData(InvalidDataException ex,
                                            HttpServletRequest request) {
        return new ErrorResponse(ex, request,
                HttpStatus.UNPROCESSABLE_ENTITY);
    }
}