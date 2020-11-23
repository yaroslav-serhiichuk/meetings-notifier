package org.home.notifier.indicator.entity;

public class Indicator {

    private Integer meetingState;

    public Indicator() {
    }

    public Indicator(Integer meetingState) {
        this.meetingState = meetingState;
    }

    public Integer getMeetingState() {
        return meetingState;
    }

    public void setMeetingState(Integer meetingState) {
        this.meetingState = meetingState;
    }
}
