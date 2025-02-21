package com.example.backend.config.exceptionHandle;

import java.io.Serial;

public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5365630128856068164L;

    public NotFoundException() {
    }

    public NotFoundException(String s) {
        super(s);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}