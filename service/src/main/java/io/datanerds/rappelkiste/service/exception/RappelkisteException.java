package io.datanerds.rappelkiste.service.exception;

public class RappelkisteException extends RuntimeException {

    public RappelkisteException(String message) {
        super(message);
    }

    public RappelkisteException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
