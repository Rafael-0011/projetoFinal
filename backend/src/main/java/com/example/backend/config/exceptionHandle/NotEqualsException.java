package com.example.backend.config.exceptionHandle;

import java.io.Serial;

public class NotEqualsException extends  RuntimeException{

    @Serial
    private static final long serialVersionUID = -5365630128856068164L;

    public NotEqualsException() {
    }

    public NotEqualsException(String s) {
        super(s);
    }

    public NotEqualsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEqualsException(Throwable cause) {
        super(cause);
    }
}
