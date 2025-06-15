package com.tecnocampus.examsimulation.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid state change")
public class InvalidStateChangeException extends RuntimeException {
    public InvalidStateChangeException() {
        super();
    }

    public InvalidStateChangeException(String msg) {
        super(msg);
    }
}
