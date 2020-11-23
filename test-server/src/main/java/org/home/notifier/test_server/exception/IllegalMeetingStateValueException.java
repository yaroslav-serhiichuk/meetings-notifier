package org.home.notifier.test_server.exception;

public class IllegalMeetingStateValueException extends IllegalArgumentException {

    public IllegalMeetingStateValueException() {
        super("Incorrect meeting state value. Possible values: 1, 2, 3");
    }
}
