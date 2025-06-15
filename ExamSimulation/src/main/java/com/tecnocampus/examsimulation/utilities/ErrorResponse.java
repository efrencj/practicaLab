package com.tecnocampus.examsimulation.utilities;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int status;
    private String message;
    private String path;
    private LocalDateTime timestamp;

    public ErrorResponse(Exception ex, HttpServletRequest request, HttpStatus httpStatus) {
        this.status = httpStatus.value();
        this.message = ex.getMessage();
        this.path = request.getRequestURI();
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
