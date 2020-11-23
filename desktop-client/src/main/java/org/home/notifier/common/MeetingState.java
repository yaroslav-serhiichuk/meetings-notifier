package org.home.notifier.common;

import java.awt.*;

import static org.home.notifier.ui.utils.ResourceImageReader.getImage;

public enum MeetingState {

    MEETING_STARTED {

        public int getValue() {
            return 1;
        }

        @Override
        public Color getColor() {
            return Color.RED;
        }

        @Override
        public Image getStatusIcon() {
            return getImage("/icons/bulb-red.png");
        }

        @Override
        public String toString() {
            return "Meeting Started";
        }
    },

    MEETING_FINISHED {

        public int getValue() {
            return 2;
        }

        @Override
        public Color getColor() {
            return new Color(0, 128, 0);
        }

        @Override
        public Image getStatusIcon() {
            return getImage("/icons/bulb-green.png");
        }

        @Override
        public String toString() {
            return "Meeting Finished";
        }
    },

    OFF {

        public int getValue() {
            return 3;
        }

        @Override
        public Color getColor() {
            return Color.DARK_GRAY;
        }

        @Override
        public Image getStatusIcon() {
            return getImage("/icons/bulb-grey.png");
        }

        @Override
        public String toString() {
            return "Indicator OFF";
        }
    },
    UNKNOWN {

        @Override
        public int getValue() {
            return -1;
        }

        @Override
        public Color getColor() {
            return Color.BLACK;
        }

        @Override
        public Image getStatusIcon() {
            return getImage("/icons/bulb-unknown.png");
        }
    };

    public abstract int getValue();

    public abstract Color getColor();

    public abstract Image getStatusIcon();

    public static MeetingState valueOf(int value) {
        switch (value) {
            case 1:
                return MeetingState.MEETING_STARTED;
            case 2:
                return MeetingState.MEETING_FINISHED;
            case 3:
                return MeetingState.OFF;
            case -1:
                return MeetingState.UNKNOWN;
            default:
                // ToDo Handle it
                throw new RuntimeException("Unknown indicator status");
        }
    }
}
