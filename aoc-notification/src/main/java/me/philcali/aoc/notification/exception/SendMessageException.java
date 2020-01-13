package me.philcali.aoc.notification.exception;

public class SendMessageException extends RuntimeException {
    private static final long serialVersionUID = -685237424268007007L;

    public SendMessageException(final Throwable ex) {
        super(ex);
    }
}
