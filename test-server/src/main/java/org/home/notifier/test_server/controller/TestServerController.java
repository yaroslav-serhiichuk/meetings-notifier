package org.home.notifier.test_server.controller;

import org.home.notifier.test_server.entity.Indicator;
import org.home.notifier.test_server.exception.IllegalMeetingStateValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestServerController {

    private final Indicator indicator;
    private final List<Integer> correctStates = List.of(1, 2, 3);

    {
        indicator = new Indicator();
        indicator.setMeetingState(3);
    }

    @GetMapping(path = "/healthCheck", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String healthCheck() {
        return "{\"indicatorHealth\": \"OK\"}";
    }

    @GetMapping(path = "/getMeetingState", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Indicator getState() throws InterruptedException {
        Thread.sleep(5);
        return indicator;
    }

    @PutMapping(path = "/setMeetingState", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void setState(@RequestBody Indicator indicator) {
        int meetingState = indicator.getMeetingState();
        if (correctStates.contains(meetingState)) {
            this.indicator.setMeetingState(meetingState);
        } else {
            throw new IllegalMeetingStateValueException();
        }
		this.indicator.setMeetingState(meetingState);
    }
}
