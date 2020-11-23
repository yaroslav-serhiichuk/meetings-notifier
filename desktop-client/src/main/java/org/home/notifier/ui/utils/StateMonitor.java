package org.home.notifier.ui.utils;

import org.home.notifier.common.PropertiesLoader;
import org.home.notifier.indicator.IndicatorClient;
import org.home.notifier.ui.MainFrame;
import org.home.notifier.ui.labels.CurrentStatusValueLabel;

public class StateMonitor implements Runnable {

    private final MainFrame mainFrame;
    private final CurrentStatusValueLabel currentStatusValueLabel;
    private final IndicatorClient indicatorClient;

    public StateMonitor(final MainFrame mainFrame,
                        final CurrentStatusValueLabel currentStatusValueLabel,
                        final IndicatorClient indicatorClient) {

        this.indicatorClient = indicatorClient;
        this.currentStatusValueLabel = currentStatusValueLabel;
        this.mainFrame = mainFrame;
    }

    @Override
    public void run() {
        start();
    }

    private void start() {
        var applicationProperties = PropertiesLoader.getProperties();
        while (true) {
            var response = indicatorClient.getIndicatorState();
            response.thenAccept(meetingState -> {
                var statusIcon = meetingState.getStatusIcon();
                mainFrame.setIconImage(statusIcon);
                mainFrame.setStatusIcon(statusIcon);
                currentStatusValueLabel.setText(meetingState.toString());
                currentStatusValueLabel.setForeground(meetingState.getColor());
                mainFrame.updateTrayIcon();
            });
            try {
                Thread.sleep(Integer.parseInt(applicationProperties.getProperty("monitoring.delay")));
            } catch (InterruptedException e) {
                // ToDo Handle it
                e.printStackTrace();
            }
        }
    }
}
