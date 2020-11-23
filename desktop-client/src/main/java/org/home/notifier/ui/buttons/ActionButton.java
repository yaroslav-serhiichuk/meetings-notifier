package org.home.notifier.ui.buttons;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.home.notifier.common.MeetingState;
import org.home.notifier.indicator.IndicatorClient;
import org.home.notifier.ui.MainFrame;
import org.home.notifier.ui.labels.CurrentStatusValueLabel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

public abstract class ActionButton extends JButton {

    private final MeetingState meetingState;
    private final MainFrame mainFrame;
    private final CurrentStatusValueLabel currentStatusValueLabel;
    private final IndicatorClient indicatorClient;

    public ActionButton(final MeetingState meetingState,
                        final MainFrame mainFrame,
                        final CurrentStatusValueLabel currentStatusValueLabel,
                        final IndicatorClient indicatorClient) {
        super();
        this.meetingState = meetingState;
        this.indicatorClient = indicatorClient;
        this.currentStatusValueLabel = currentStatusValueLabel;
        this.mainFrame = mainFrame;
        this.addActionListener(new ButtonActionListener());
    }

    private class ButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                CompletableFuture<Integer> response = indicatorClient.setIndicatorState(meetingState);
                response.thenAccept(r -> {
                    switch (r) {
                        case 202:
                            var statusIcon = meetingState.getStatusIcon();
                            mainFrame.setIconImage(statusIcon);
                            mainFrame.setStatusIcon(statusIcon);
                            currentStatusValueLabel.setText(meetingState.toString());
                            currentStatusValueLabel.setForeground(meetingState.getColor());
                            mainFrame.updateTrayIcon();
                            break;
                        case 408:
                            // ToDo Handle it
                            break;
                    }
                });
            } catch (JsonProcessingException ex) {
                // ToDo Handle it
                ex.printStackTrace();
            }
        }
    }
}
