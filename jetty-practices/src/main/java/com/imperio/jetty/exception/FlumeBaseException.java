package com.imperio.jetty.exception;

public class FlumeBaseException extends  Exception {

    public FlumeBaseException() {
        super();
    }

    public FlumeBaseException(String message) {
        super(message);
    }

    public FlumeBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
