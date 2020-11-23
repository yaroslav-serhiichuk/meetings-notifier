package org.home.notifier.test_server.entity;

public class Indicator {

    private volatile Integer meetingState;

    public Integer getMeetingState() {
        return meetingState;
    }

    public void setMeetingState(Integer meetingState) {
        this.meetingState = meetingState;
    }
}
