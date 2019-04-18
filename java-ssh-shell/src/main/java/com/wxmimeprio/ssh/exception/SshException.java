package com.wxmimeprio.ssh.exception;

public class SshException extends Exception {

    public SshException() {
        super();
    }

    public SshException(String message) {
        super(message);
    }

    public SshException(String message, Throwable cause) {
        super(message, cause);
    }
}
