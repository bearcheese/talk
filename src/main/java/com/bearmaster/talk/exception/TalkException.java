package com.bearmaster.talk.exception;

public class TalkException extends Exception {
    
    private static final long serialVersionUID = -1695783413277882978L;

    public TalkException(String message) {
        super(message);
    }
    
    public TalkException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public TalkException(Throwable cause) {
        super(cause);
    }

}
