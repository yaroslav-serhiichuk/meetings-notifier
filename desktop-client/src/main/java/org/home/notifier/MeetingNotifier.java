package org.home.notifier;

import org.home.notifier.common.PropertiesLoader;
import org.home.notifier.ui.MainFrame;

public class MeetingNotifier {

    public static void main(String[] args) {
        var prop = PropertiesLoader.getProperties();
        new MainFrame();
    }
}
