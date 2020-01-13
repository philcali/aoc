package me.philcali.aoc.notification;

import me.philcali.aoc.notification.exception.SendMessageException;

public interface MessageService {
    void send(String message) throws SendMessageException;
}
