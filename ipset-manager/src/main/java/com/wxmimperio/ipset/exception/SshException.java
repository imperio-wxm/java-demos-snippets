package com.wxmimperio.ipset.exception;

public class SshException extends Exception {

    public SshException(String message) {
        super(message);
    }

    public SshException(String message, Throwable cause) {
        super(message, cause);
    }
}
